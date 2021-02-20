//package io.kettil.rewrite.expand;
//
//import io.kettil.AclService;
//import io.kettil.rewrite.expand.expression.*;
//import io.kettil.rewrite.userset.expression.*;
//import io.kettil.tuple.Tuple;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//public class ExpandExpressionBuilder {
//    //TODO: fix tuple vs userset parsing
//    private final static Pattern usersetPattern = Pattern.compile("(\\S+):(\\S+)#(\\S+)");
//
//    //TODO: fix this too
//    private static final String TUPLE_USERSET_OBJECT = "$TUPLE_USERSET_OBJECT";
//    private static final String TUPLE_USERSET_RELATION = "$TUPLE_USERSET_RELATION";
//
//    public static ExpandExpr eval(AclService repo, String namespace, String objectId, String relation) {
//        Visitor visitor = new Visitor(repo);
//        ExpandExpr eval = visitor.eval(namespace, objectId, relation);
//        return eval;
//    }
//
//    private static class Visitor implements UsersetExprVisitor<ExpandExpr> {
//        private final AclService repo;
//
//        private NamespaceConfigExpr namespaceConfig;
//        private String objectId;
//        private String relation;
//
//        private Visitor(AclService repo) {
//            this.repo = repo;
//        }
//
//        public ExpandExpr eval(String namespace, String objectId, String relation) {
//            return eval(repo.getNamespaceConfig(namespace), objectId, relation);
//        }
//
//        //TODO: ugly--fix
////        private boolean isUserset(String user) {
////            return user.contains(":");
////        }
//
//        public ExpandExpr eval(NamespaceConfigExpr namespaceConfig, String objectId, String relation) {
//            this.namespaceConfig = namespaceConfig;
//            this.objectId = objectId;
//            this.relation = relation;
//
//            return this.visitNamespaceConfigExpr(namespaceConfig);
//        }
//
//        @Override
//        public ExpandExpr visitNamespaceConfigExpr(NamespaceConfigExpr expr) {
//            RelationConfigExpr relation = expr.getRelations().get(this.relation);
//            if (relation == null)
//                throw new IllegalArgumentException("Relation does not exist: " + this.relation);
//
//            return visitRelationConfigExpr(relation);
//        }
//
//        @Override
//        public ExpandExpr visitRelationConfigExpr(RelationConfigExpr expr) {
//            return expr.accept(this);
//        }
//
//        @Override
//        public ExpandExpr visitChildUsersetExpr(ChildUsersetExpr expr) {
//            ExpandExpr unrecursed = expr.getUserset().accept(this);
//
//            Set<String> users = new HashSet<>();
//
//            for (String u : unrecursed.users()) {
//                users.addAll(recurseUserset(u));
//            }
//
//            return new ChildExpandExpr(unrecursed, users);
//        }
//
//        private Set<String> recurseUserset(String user) {
//            Set<String> users = new HashSet<>();
//
//            Matcher m = usersetPattern.matcher(user);
//            if (!m.find()) { //Endcase: user is a userId
//                users.add(user);
//                return users;
//            }
//
//            Set<String> usersByObjectIdAndRelation = repo.getUsersByObjectIdAndRelation(
//                m.group(1),
//                m.group(2),
//                m.group(3));
//
//            for (String u : usersByObjectIdAndRelation) {
//                if (!isUserset(u)) {
//                    users.add(u);
//                    continue;
//                }
//
//                users.addAll(recurseUserset(u));
//            }
//
//            return users;
//        }
//
//        @Override
//        public ComputedExpandExpr visitComputedUsersetExpr(ComputedUsersetExpr expr) {
//            String objectId = expr.getObject() != null ? expr.getObject() : this.objectId;
//            String relation = expr.getRelation() != null ? expr.getRelation() : this.relation;
//
//            Visitor visitor = new Visitor(repo);
//
//            return new ComputedExpandExpr(
//                objectId,
//                relation,
//                visitor.eval(
//                    namespaceConfig,
//                    objectId,
//                    relation));
//        }
//
//        @Override
//        public ExpandExpr visitThisUsersetExpr(ThisUsersetExpr expr) {
//            return new ThisExpandExpr(
//                repo.getUsersByObjectIdAndRelation(
//                    namespaceConfig.getName(),
//                    objectId,
//                    relation));
//        }
//
//        @Override
//        public SetOperationExpandExpr visitSetOperationExpr(SetOperationUsersetExpr setOperationExpr) {
//            List<ExpandExpr> children = setOperationExpr.getChildren().stream()
//                .map(i -> i.accept(this))
//                .collect(Collectors.toList());
//
//            Set<String> users = null;
//
//            loop:
//            for (ExpandExpr child : children) {
//                if (users == null) {
//                    users = new HashSet<>(child.users());
//                    continue;
//                }
//
//                switch (setOperationExpr.getOp()) {
//                    case Union:
//                        users.addAll(child.users());
//                        break;
//
//                    case Intersect:
//                        users.retainAll(child.users());
//                        if (users.size() == 0)
//                            break loop;
//
//                        break;
//
//                    case Exclude:
//                        users.removeAll(child.users());
//                        if (users.size() == 0)
//                            break loop;
//
//                        break;
//                }
//            }
//
//            return new SetOperationExpandExpr(
//                SetOperationExpandExpr.Op.valueOf(setOperationExpr.getOp().name()), //TODO: ugly--fix
//                children,
//                users);
//        }
//
//        @Override
//        public TuplesetExpandExpr visitTuplesetExpr(TuplesetExpr expr) {
//            String objectId = expr.getObject() != null ? expr.getObject() : this.objectId;
//            String relation = expr.getRelation() != null ? expr.getRelation() : this.relation;
//
//            Set<Tuple> tuples = repo.getTuplesByObjectIdAndRelation(
//                namespaceConfig.getName(),
//                objectId,
//                relation);
//
//            return new TuplesetExpandExpr(
//                expr.getObject(),
//                expr.getRelation(),
//                tuples);
//        }
//
//        @Override
//        public TupleToUsersetExpandExpr visitTupleToUsersetExpr(TupleToUsersetExpr expr) {
//            TuplesetExpandExpr tuplesetExpandExpr = visitTuplesetExpr(expr.getTupleset());
//            Set<Tuple> tuples = tuplesetExpandExpr.getTuples();
//
//            Set<String> users = new HashSet<>();
//
//            List<ExpandExpr> children = new ArrayList<>();
//
//            for (Tuple tuple : tuples) {
//                String objectId = expr.getComputedUserset().getObject() != null
//                    ? expr.getComputedUserset().getObject()
//                    : this.objectId;
//
//                String relation = expr.getComputedUserset().getRelation() != null
//                    ? expr.getComputedUserset().getRelation()
//                    : this.relation;
//
//                if (TUPLE_USERSET_OBJECT.equals(objectId)) {
//                    objectId = tuple.getUsersetObjectId();
//                }
//
//                if (TUPLE_USERSET_RELATION.equals(relation)) {
//                    relation = tuple.getUsersetRelation();
//                }
//
//                ExpandExpr eval = new Visitor(repo).eval(namespaceConfig, objectId, relation);
//
//                children.add(eval);
//                users.addAll(eval.users());
//            }
//
//            ComputedExpandExpr computedExpandExpr = new ComputedExpandExpr(
//                expr.getComputedUserset().getObject(),
//                expr.getComputedUserset().getRelation(),
//                new SetOperationExpandExpr(SetOperationExpandExpr.Op.Union, children, users));
//
//            return new TupleToUsersetExpandExpr(
//                tuplesetExpandExpr,
//                computedExpandExpr);
//        }
//    }
//}
