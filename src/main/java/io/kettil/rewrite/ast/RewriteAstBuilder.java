package io.kettil.rewrite.ast;

import io.kettil.rewrite.parser.UsersetRewriteBaseVisitor;
import io.kettil.rewrite.parser.UsersetRewriteParser;

class RewriteAstBuilder extends UsersetRewriteBaseVisitor<RewriteAst> {
    @Override
    public RewriteAst visitNamespace(UsersetRewriteParser.NamespaceContext ctx) {
        NamespaceAst namespace = new NamespaceAst();

        namespace.setName(unquote(ctx.namespaceName.getText()));

        for (UsersetRewriteParser.RelationContext relationContext : ctx.relation()) {
            namespace.getRelations().add((RelationAst) visitRelation(relationContext));
        }

        return namespace;
    }

    @Override
    public RewriteAst visitRelation(UsersetRewriteParser.RelationContext ctx) {
        RelationAst relation = new RelationAst();
        relation.setName(unquote(ctx.relationName.getText()));

        if (ctx.usersetRewrite() != null) {
            //For now, userset_rewrite seems to be just a syntactic container -- ignore
            relation.setUsersetRewrite((UsersetRewriteAst) visitUsersetRewrite(ctx.usersetRewrite()));
        }

        return relation;
    }

    @Override
    public RewriteAst visitUsersetRewrite(UsersetRewriteParser.UsersetRewriteContext ctx) {
        return new UsersetRewriteAst(visitUserset(ctx.userset()));
    }

    @Override
    public RewriteAst visitUserset(UsersetRewriteParser.UsersetContext ctx) {
        return super.visitUserset(ctx);
    }

    @Override
    public RewriteAst visitChildUserset(UsersetRewriteParser.ChildUsersetContext ctx) {
        return new ChildUsersetAst(super.visitUserset(ctx.userset()));
    }

    @Override
    public RewriteAst visitComputedUserset(UsersetRewriteParser.ComputedUsersetContext ctx) {
        ComputedUsersetAst expression = new ComputedUsersetAst();

        if (ctx.objectRef().size() > 1)
            throw new RuntimeException("More than one object specified"); //TODO: figure out which exception to throw
        if (!ctx.objectRef().isEmpty()) {
            UsersetRewriteParser.ObjectRefContext objectRefContext = ctx.objectRef().get(0);

            switch (objectRefContext.ref.getType()) {
                case UsersetRewriteParser.STRING:
                    expression.setObject(unquote(objectRefContext.STRING().getText()));
                    break;

                case UsersetRewriteParser.TUPLE_USERSET_OBJECT:
                    expression.setObject(objectRefContext.TUPLE_USERSET_OBJECT().getText());
                    break;
            }
        }

        if (ctx.relationRef().size() > 1)
            throw new RuntimeException("More than one relation specified"); //TODO: figure out which exception to throw
        if (!ctx.relationRef().isEmpty()) {
            UsersetRewriteParser.RelationRefContext relationRefContext = ctx.relationRef().get(0);
            switch (relationRefContext.ref.getType()) {
                case UsersetRewriteParser.STRING:
                    expression.setRelation(unquote(relationRefContext.STRING().getText()));
                    break;

                case UsersetRewriteParser.TUPLE_USERSET_RELATION:
                    expression.setRelation(relationRefContext.TUPLE_USERSET_RELATION().getText());
                    break;
            }
        }

        return expression;
    }

    @Override
    public RewriteAst visitThisUserset(UsersetRewriteParser.ThisUsersetContext ctx) {
        return new ThisUsersetAst();
    }

    @Override
    public RewriteAst visitTupleToUserset(UsersetRewriteParser.TupleToUsersetContext ctx) {
        return new TupleToUsersetAst(
            (TuplesetAst) visitTupleset(ctx.tupleset()),
            (ComputedUsersetAst) visitComputedUserset(ctx.computedUserset()));
    }

    @Override
    public RewriteAst visitTupleset(UsersetRewriteParser.TuplesetContext ctx) {
        TuplesetAst expression = new TuplesetAst();

        if (ctx.objectRef().size() > 1)
            throw new RuntimeException("More than one object specified"); //TODO: figure out which exception to throw
        if (!ctx.objectRef().isEmpty()) {
            UsersetRewriteParser.ObjectRefContext objectRefContext = ctx.objectRef().get(0);

            switch (objectRefContext.ref.getType()) {
                case UsersetRewriteParser.STRING:
                    expression.setObject(unquote(objectRefContext.STRING().getText()));
                    break;

                case UsersetRewriteParser.TUPLE_USERSET_OBJECT:
                    expression.setObject(objectRefContext.TUPLE_USERSET_OBJECT().getText());
                    break;
            }
        }

        if (ctx.relationRef().size() > 1)
            throw new RuntimeException("More than one relation specified"); //TODO: figure out which exception to throw
        if (!ctx.relationRef().isEmpty()) {
            UsersetRewriteParser.RelationRefContext relationRefContext = ctx.relationRef().get(0);
            switch (relationRefContext.ref.getType()) {
                case UsersetRewriteParser.STRING:
                    expression.setRelation(unquote(relationRefContext.STRING().getText()));
                    break;

                case UsersetRewriteParser.TUPLE_USERSET_RELATION:
                    expression.setRelation(relationRefContext.TUPLE_USERSET_RELATION().getText());
                    break;
            }
        }

        return expression;
    }

    @Override
    public RewriteAst visitSetOperationUserset(UsersetRewriteParser.SetOperationUsersetContext ctx) {
        return super.visitSetOperationUserset(ctx);
    }

    @Override
    public RewriteAst visitUnionUserset(UsersetRewriteParser.UnionUsersetContext ctx) {
        UnionUsersetAst expression = new UnionUsersetAst();

        for (UsersetRewriteParser.UsersetContext i : ctx.userset()) {
            expression.getChildren().add(visitUserset(i));
        }

        return expression;
    }

    @Override
    public RewriteAst visitIntersectUserset(UsersetRewriteParser.IntersectUsersetContext ctx) {
        IntersectUsersetAst expression = new IntersectUsersetAst();

        for (UsersetRewriteParser.UsersetContext i : ctx.userset()) {
            expression.getChildren().add(visitUserset(i));
        }

        return expression;
    }

    @Override
    public RewriteAst visitExcludeUserset(UsersetRewriteParser.ExcludeUsersetContext ctx) {
        ExcludeUsersetAst expression = new ExcludeUsersetAst();

        for (UsersetRewriteParser.UsersetContext i : ctx.userset()) {
            expression.getChildren().add(visitUserset(i));
        }

        return expression;
    }

    @Override
    public RewriteAst visitObjectRef(UsersetRewriteParser.ObjectRefContext ctx) {
        return super.visitObjectRef(ctx);
    }

    @Override
    public RewriteAst visitRelationRef(UsersetRewriteParser.RelationRefContext ctx) {
        return super.visitRelationRef(ctx);
    }

    private String unquote(String value) {
        return value.substring(1, value.length() - 1);
    }
}
