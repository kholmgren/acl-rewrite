package io.kettil.rewrite.userset.expression.parse;

import io.kettil.rewrite.parser.UsersetRewriteBaseVisitor;
import io.kettil.rewrite.parser.UsersetRewriteLexer;
import io.kettil.rewrite.parser.UsersetRewriteParser;
import io.kettil.rewrite.userset.expression.SetOp;
import io.kettil.rewrite.userset.expression.UsersetRef;
import io.kettil.rewrite.userset.expression.parse.ast.*;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.InputStream;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class NamespaceAstFactory {
    public static NamespaceAst parse(String text) {
        return parse(CharStreams.fromString(text));
    }

    @SneakyThrows
    public static NamespaceAst parse(InputStream inputStream) {
        return parse(CharStreams.fromStream(inputStream));
    }

    private static NamespaceAst parse(CharStream input) {
        UsersetRewriteParser parser = new UsersetRewriteParser(new CommonTokenStream(new UsersetRewriteLexer(input)));
        return (NamespaceAst) new Builder().visit(parser.namespace());
    }

    private static class Builder extends UsersetRewriteBaseVisitor<RewriteAst> {
        @Override
        public NamespaceAst visitNamespace(UsersetRewriteParser.NamespaceContext ctx) {
            return new NamespaceAst(
                unquote(ctx.namespaceName.getText()),
                ctx.relation().stream()
                    .map(this::visitRelation)
                    .collect(toList()));
        }

        @Override
        public RelationAst visitRelation(UsersetRewriteParser.RelationContext ctx) {
            return new RelationAst(
                unquote(ctx.relationName.getText()),
                ctx.usersetRewrite() != null
                    ? visitUsersetRewrite(ctx.usersetRewrite())
                    : null);
        }

        @Override
        public UsersetRewriteAst visitUsersetRewrite(UsersetRewriteParser.UsersetRewriteContext ctx) {
            return new UsersetRewriteAst(visitUserset(ctx.userset()));
        }

        @Override
        public ChildUsersetAst visitChildUserset(UsersetRewriteParser.ChildUsersetContext ctx) {
            return new ChildUsersetAst(super.visitUserset(ctx.userset()));
        }

        @Override
        public ComputedUsersetAst visitComputedUserset(UsersetRewriteParser.ComputedUsersetContext ctx) {
            String namespace = null;
            if (ctx.usersetNamespaceRef().size() > 1)
                throw new RuntimeException("More than one namespace specified"); //TODO: figure out which exception to throw
            if (!ctx.usersetNamespaceRef().isEmpty()) {
                UsersetRewriteParser.UsersetNamespaceRefContext usersetNamespaceRefContext = ctx.usersetNamespaceRef().get(0);

                switch (usersetNamespaceRefContext.ref.getType()) {
                    case UsersetRewriteParser.STRING:
                        namespace = unquote(usersetNamespaceRefContext.STRING().getText());
                        break;

                    case UsersetRewriteParser.TUPLE_USERSET_NAMESPACE:
                        namespace = UsersetRef.TUPLE_USERSET_NAMESPACE;
                        break;
                }
            }

            String object = null;
            if (ctx.usersetObjectRef().size() > 1)
                throw new RuntimeException("More than one object specified"); //TODO: figure out which exception to throw
            if (!ctx.usersetObjectRef().isEmpty()) {
                UsersetRewriteParser.UsersetObjectRefContext usersetObjectRefContext = ctx.usersetObjectRef().get(0);

                switch (usersetObjectRefContext.ref.getType()) {
                    case UsersetRewriteParser.STRING:
                        object = unquote(usersetObjectRefContext.STRING().getText());
                        break;

                    case UsersetRewriteParser.TUPLE_USERSET_OBJECT:
                        object = UsersetRef.TUPLE_USERSET_OBJECT;
                        break;
                }
            }

            String relation = null;
            if (ctx.usersetRelationRef().size() > 1)
                throw new RuntimeException("More than one relation specified"); //TODO: figure out which exception to throw
            if (!ctx.usersetRelationRef().isEmpty()) {
                UsersetRewriteParser.UsersetRelationRefContext usersetRelationRefContext = ctx.usersetRelationRef().get(0);
                switch (usersetRelationRefContext.ref.getType()) {
                    case UsersetRewriteParser.STRING:
                        relation = unquote(usersetRelationRefContext.STRING().getText());
                        break;

                    case UsersetRewriteParser.TUPLE_USERSET_RELATION:
                        relation = UsersetRef.TUPLE_USERSET_RELATION;
                        break;
                }
            }

            return new ComputedUsersetAst(namespace, object, relation);
        }

        @Override
        public ThisUsersetAst visitThisUserset(UsersetRewriteParser.ThisUsersetContext ctx) {
            return new ThisUsersetAst();
        }

        @Override
        public TupleToUsersetAst visitTupleToUserset(UsersetRewriteParser.TupleToUsersetContext ctx) {
            return new TupleToUsersetAst(
                visitTupleset(ctx.tupleset()),
                visitComputedUserset(ctx.computedUserset()));
        }

        @Override
        public TuplesetAst visitTupleset(UsersetRewriteParser.TuplesetContext ctx) {
            String namespace = null;
            if (ctx.namespaceRef().size() > 1)
                throw new RuntimeException("More than one namespace specified"); //TODO: figure out which exception to throw
            if (!ctx.namespaceRef().isEmpty())
                namespace = unquote(ctx.namespaceRef().get(0).ref.getText());

            String object = null;
            if (ctx.objectRef().size() > 1)
                throw new RuntimeException("More than one object specified"); //TODO: figure out which exception to throw
            if (!ctx.objectRef().isEmpty())
                object = unquote(ctx.objectRef().get(0).ref.getText());

            String relation = null;
            if (ctx.relationRef().size() > 1)
                throw new RuntimeException("More than one relation specified"); //TODO: figure out which exception to throw
            if (!ctx.relationRef().isEmpty())
                relation = unquote(ctx.relationRef().get(0).ref.getText());

            return new TuplesetAst(namespace, object, relation);
        }

        @Override
        public SetOperationUsersetAst visitSetOperationUserset(UsersetRewriteParser.SetOperationUsersetContext ctx) {
            SetOp op;
            switch (ctx.op.getType()) {
                case UsersetRewriteParser.UNION:
                    op = SetOp.Union;
                    break;
                case UsersetRewriteParser.INTERSECT:
                    op = SetOp.Intersect;
                    break;
                case UsersetRewriteParser.EXCLUDE:
                    op = SetOp.Exclude;
                    break;
                default:
                    throw new IllegalStateException();
            }

            return new SetOperationUsersetAst(
                op,
                ctx.userset().stream()
                    .map(i -> i.accept(this))
                    .collect(toList()));
        }

        private String unquote(String value) {
            return value.substring(1, value.length() - 1);
        }
    }
}
