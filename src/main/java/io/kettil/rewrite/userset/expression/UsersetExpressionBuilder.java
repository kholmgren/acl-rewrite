package io.kettil.rewrite.userset.expression;

import io.kettil.rewrite.ast.*;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Data
public class UsersetExpressionBuilder {
    public static NamespaceExpr build(NamespaceAst ast) {
        return new Builder().visitNamespaceAst(ast);
    }

    private static class Builder implements RewriteAstVisitor<UsersetExpr> {
        private UsersetExpr.Context context;

        @Override
        public NamespaceExpr visitNamespaceAst(NamespaceAst ast) {
            context = new UsersetExpr.Context(ast.getName(), null);

            Map<String, UsersetExpr> relations = new LinkedHashMap<>();
            for (RelationAst r : ast.getRelations()) {
                UsersetExpr rel = visitRelationAst(r);
                relations.put(context.getRelation(), rel);
            }

            return new NamespaceExpr(context.getNamespace(), relations); //TODO: NamespaceExpr really necessary?
        }

        @Override
        public UsersetExpr visitRelationAst(RelationAst ast) {
            context = new UsersetExpr.Context(context.getNamespace(), ast.getName());
            return visitUsersetRewriteAst(ast.getUsersetRewrite());
        }

        @Override
        public UsersetExpr visitUsersetRewriteAst(UsersetRewriteAst ast) {
            if (ast == null) {
                return new ChildUsersetExpr(context, new ThisUsersetExpr(context));
            } else
                return ast.getUserset().accept(this);
        }

        @Override
        public ChildUsersetExpr visitChildUsersetAst(ChildUsersetAst ast) {
            return new ChildUsersetExpr(context, ast.getUserset().accept(this));
        }

        @Override
        public ComputedUsersetExpr visitComputedUsersetAst(ComputedUsersetAst ast) {
            return new ComputedUsersetExpr(context, ast.getObject(), ast.getRelation());
        }

        @Override
        public ThisUsersetExpr visitThisUsersetAst(ThisUsersetAst ast) {
            return new ThisUsersetExpr(context);
        }

        @Override
        public TupleToUsersetExpr visitTupleToUsersetAst(TupleToUsersetAst ast) {
            return new TupleToUsersetExpr(context,
                visitTuplesetAst(ast.getTupleset()),
                visitComputedUsersetAst(ast.getComputedUserset()));
        }

        @Override
        public TuplesetExpr visitTuplesetAst(TuplesetAst ast) {
            return new TuplesetExpr(context, ast.getObject(), ast.getRelation());
        }

        @Override
        public UnionUsersetExpr visitUnionUsersetAst(UnionUsersetAst ast) {
            return new UnionUsersetExpr(context, ast.getChildren().stream()
                .map(i -> i.accept(this))
                .collect(Collectors.toList()));
        }

        @Override
        public IntersectUsersetExpr visitIntersectUsersetAst(IntersectUsersetAst ast) {
            return new IntersectUsersetExpr(context, ast.getChildren().stream()
                .map(i -> i.accept(this))
                .collect(Collectors.toList()));
        }

        @Override
        public ExcludeUsersetExpr visitExcludeUsersetAst(ExcludeUsersetAst ast) {
            return new ExcludeUsersetExpr(context, ast.getChildren().stream()
                .map(i -> i.accept(this))
                .collect(Collectors.toList()));
        }
    }
}
