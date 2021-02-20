package io.kettil.rewrite.userset;

import io.kettil.rewrite.userset.expression.*;
import io.kettil.rewrite.userset.expression.parse.NamespaceAstFactory;
import io.kettil.rewrite.userset.expression.parse.ast.*;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.stream.Collectors;

@UtilityClass
public class NamespaceConfigExprFactory {
    public static NamespaceUsersetExpr parse(NamespaceAst ast) {
        return new Builder().visitNamespaceAst(ast);
    }

    public static NamespaceUsersetExpr parse(String text) {
        return new Builder().visitNamespaceAst(NamespaceAstFactory.parse(text));
    }

    @SneakyThrows
    public static NamespaceUsersetExpr parse(InputStream inputStream) {
        return new Builder().visitNamespaceAst(NamespaceAstFactory.parse(inputStream));
    }

    private static class Builder implements RewriteAst.Visitor<UsersetExpr> {
        @Override
        public NamespaceUsersetExpr visitNamespaceAst(NamespaceAst ast) {
            return new NamespaceUsersetExpr(
                ast.getName(),
                ast.getRelations().stream()
                    .map(this::visitRelationAst)
                    .collect(Collectors.toMap(
                        RelationUsersetExpr::getName,
                        r -> r)));
        }

        @Override
        public RelationUsersetExpr visitRelationAst(RelationAst ast) {
            return ast.getUsersetRewrite() == null
                ? new RelationUsersetExpr(ast.getName(), new ChildUsersetExpr(new ThisUsersetExpr()))
                : new RelationUsersetExpr(ast.getName(), ast.getUsersetRewrite().accept(this));
        }

        @Override
        public UsersetExpr visitUsersetRewriteAst(UsersetRewriteAst ast) {
            return ast == null
                ? new ChildUsersetExpr(new ThisUsersetExpr())
                : ast.getUserset().accept(this);
        }

        @Override
        public ChildUsersetExpr visitChildUsersetAst(ChildUsersetAst ast) {
            return new ChildUsersetExpr(
                ast.getUserset().accept(this));
        }

        @Override
        public ComputedUsersetExpr visitComputedUsersetAst(ComputedUsersetAst ast) {
            String namespace = ast.getNamespace();
            String object = ast.getObject();

            if (namespace == null && UsersetRef.TUPLE_USERSET_OBJECT.equals(object))
                namespace = UsersetRef.TUPLE_USERSET_NAMESPACE;

            return new ComputedUsersetExpr(
                namespace,
                object,
                ast.getRelation());
        }

        @Override
        public ThisUsersetExpr visitThisUsersetAst(ThisUsersetAst ast) {
            return new ThisUsersetExpr();
        }

        @Override
        public TupleToUsersetExpr visitTupleToUsersetAst(TupleToUsersetAst ast) {
            return new TupleToUsersetExpr(
                visitTuplesetAst(ast.getTupleset()),
                visitComputedUsersetAst(ast.getComputedUserset()));
        }

        @Override
        public TuplesetExpr visitTuplesetAst(TuplesetAst ast) {
            return new TuplesetExpr(ast.getNamespace(), ast.getObject(), ast.getRelation());
        }

        @Override
        public UsersetExpr visitSetOperationUsersetAst(SetOperationUsersetAst ast) {
            return new SetOperationUsersetExpr(ast.getOp(), ast.getChildren().stream()
                .map(i -> i.accept(this))
                .collect(Collectors.toList()));
        }
    }
}
