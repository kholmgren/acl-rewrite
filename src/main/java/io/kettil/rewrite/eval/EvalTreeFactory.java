package io.kettil.rewrite.eval;

import io.kettil.AclKey;
import io.kettil.AclRelation;
import io.kettil.AclService;
import io.kettil.rewrite.userset.expression.*;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class EvalTreeFactory {
    public static EvalTree eval(AclService service, AclKey object) {
        return new Builder(service, object).eval();
    }

    @RequiredArgsConstructor
    private static class Builder implements UsersetExpr.Visitor<EvalTree> {
        private final AclService service;
        private final AclKey object;

        public EvalTree eval() {
            NamespaceUsersetExpr namespaceConfig = service.getNamespaceConfig(object.getNamespace());
            requireNonNull(namespaceConfig);
            return visitNamespaceUsersetExpr(namespaceConfig);
        }

        @Override
        public EvalTree visitChildUsersetExpr(ChildUsersetExpr expr) {
            EvalTree eval = expr.getUserset().accept(this);

            Stack<AclRelation> relations = new Stack<>();
            relations.addAll(eval.getResult().stream()
                .flatMap(i -> service.getRelations(i).stream())
                .collect(toList()));

            Set<AclKey> result = new TreeSet<>();
            while (!relations.empty()) {
                AclKey user = relations.pop().getUser();
                if (user.idOnly())
                    result.add(user);
                else
                    relations.addAll(service.getRelations(user));
            }

            return new EvalTree(
                expr,
                singletonList(eval),
                result);
        }

        @Override
        public EvalTree visitComputedUsersetExpr(ComputedUsersetExpr expr) {
            EvalTree eval = new Builder(
                service,
                new AclKey(
                    requireNonNullElse(expr.getNamespace(), object.getNamespace()),
                    requireNonNullElse(expr.getObject(), object.getId()),
                    requireNonNullElse(expr.getRelation(), object.getRelation())))
                .eval();

            return new EvalTree(
                expr,
                singletonList(eval),
                eval.getResult());
        }

        @Override
        public EvalTree visitNamespaceUsersetExpr(NamespaceUsersetExpr expr) {
            RelationUsersetExpr r = expr.getRelations().get(object.getRelation());
            requireNonNull(r);
            return visitRelationUsersetExpr(r);
        }

        @Override
        public EvalTree visitRelationUsersetExpr(RelationUsersetExpr expr) {
            return expr.getRewrite().accept(this);
        }

        @Override
        public EvalTree visitSetOperationExpr(SetOperationUsersetExpr expr) {
            List<EvalTree> children = expr.getChildren().stream()
                .map(i -> i.accept(this))
                .collect(toList());

            Set<AclKey> result = null;

            loop:
            for (EvalTree child : children)
                if (result == null)
                    result = new TreeSet<>(child.getResult());
                else
                    switch (expr.getOp()) {
                        case Union:
                            result.addAll(child.getResult());
                            break;
                        case Intersect:
                            result.retainAll(child.getResult());
                            if (result.size() == 0)
                                break loop;
                            break;
                        case Exclude:
                            result.removeAll(child.getResult());
                            if (result.size() == 0)
                                break loop;
                            break;
                        default:
                            throw new IllegalStateException();
                    }

            return new EvalTree(expr, children, result);
        }

        @Override
        public EvalTree visitThisUsersetExpr(ThisUsersetExpr expr) {
            return new EvalTree(expr,
                Collections.EMPTY_LIST,
                service.getRelations(object).stream()
                    .map(AclRelation::getUser)
                    .collect(toCollection((Supplier<TreeSet<AclKey>>) TreeSet::new)));
        }

        @Override
        public EvalTree visitTuplesetExpr(TuplesetExpr expr) {
            throw new AssertionError("Should never get here");
        }

        @Override
        public EvalTree visitTupleToUsersetExpr(TupleToUsersetExpr expr) {
            Set<AclRelation> tupleset = service.getRelations(
                new AclKey(
                    requireNonNullElse(expr.getTupleset().getNamespace(), object.getNamespace()),
                    requireNonNullElse(expr.getTupleset().getObject(), object.getId()),
                    requireNonNullElse(expr.getTupleset().getRelation(), object.getRelation())));

            List<EvalTree> children = new ArrayList<>();
            Set<AclKey> result = new TreeSet<>();

            for (AclRelation r : tupleset) {
                String namespace = requireNonNullElse(expr.getComputedUserset().getNamespace(), r.getObject().getNamespace());
                if (UsersetRef.TUPLE_USERSET_NAMESPACE.equals(namespace))
                    namespace = r.getUser().getNamespace();

                String id = requireNonNullElse(expr.getComputedUserset().getObject(), r.getObject().getId());
                if (UsersetRef.TUPLE_USERSET_OBJECT.equals(id))
                    id = r.getUser().getId();

                String relation = requireNonNullElse(expr.getComputedUserset().getRelation(), r.getObject().getRelation());
                if (UsersetRef.TUPLE_USERSET_RELATION.equals(relation))
                    relation = r.getUser().getRelation();

                EvalTree eval = new Builder(
                    service,
                    new AclKey(
                        namespace, id, relation))
                    .eval();

                children.add(
                    new EvalTree(
                        new ComputedUsersetExpr(namespace, id, relation),
                        singletonList(eval),
                        eval.getResult()));

                result.addAll(eval.getResult());
            }

            return new EvalTree(expr, children, result);
        }
    }
}
