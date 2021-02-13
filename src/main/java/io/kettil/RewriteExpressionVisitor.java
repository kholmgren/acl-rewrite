package io.kettil;

import io.kettil.ast.*;

public class RewriteExpressionVisitor extends RewriteBaseVisitor<RewriteExpression> {
    @Override
    public RewriteExpression visitNamespace(RewriteParser.NamespaceContext ctx) {
        Namespace namespace = new Namespace();

        namespace.setName(unquote(ctx.namespaceName.getText()));

        for (RewriteParser.RelationContext relationContext : ctx.relation()) {
            namespace.getRelations().add((Relation) visitRelation(relationContext));
        }

        return namespace;
    }

    @Override
    public RewriteExpression visitRelation(RewriteParser.RelationContext ctx) {
        Relation relation = new Relation();
        relation.setName(unquote(ctx.relationName.getText()));

        if (ctx.usersetRewrite() != null) {
            //For now, userset_rewrite seems to be just a syntactic container -- ignore
            relation.setUsersetRewrite((UsersetRewriteExpression) visitUsersetRewrite(ctx.usersetRewrite()));
        }

        return relation;
    }

    @Override
    public RewriteExpression visitUsersetRewrite(RewriteParser.UsersetRewriteContext ctx) {
        return new UsersetRewriteExpression(visitUserset(ctx.userset()));
    }

    @Override
    public RewriteExpression visitUserset(RewriteParser.UsersetContext ctx) {
        return super.visitUserset(ctx);
    }

    @Override
    public RewriteExpression visitChildUserset(RewriteParser.ChildUsersetContext ctx) {
        return new ChildUsersetExpression(super.visitUserset(ctx.userset()));
    }

    @Override
    public RewriteExpression visitComputedUserset(RewriteParser.ComputedUsersetContext ctx) {
        ComputedUsersetExpression expression = new ComputedUsersetExpression();

        if (ctx.objectRef().size() > 1)
            throw new RuntimeException("More than one object specified"); //TODO: figure out which exception to throw
        if (!ctx.objectRef().isEmpty()) {
            RewriteParser.ObjectRefContext objectRefContext = ctx.objectRef().get(0);

            switch (objectRefContext.ref.getType()) {
                case RewriteParser.STRING:
                    expression.setObject(unquote(objectRefContext.STRING().getText()));
                    break;

                case RewriteParser.TUPLE_USERSET_OBJECT:
                    expression.setObject(objectRefContext.TUPLE_USERSET_OBJECT().getText());
                    break;
            }
        }

        if (ctx.relationRef().size() > 1)
            throw new RuntimeException("More than one relation specified"); //TODO: figure out which exception to throw
        if (!ctx.relationRef().isEmpty()) {
            RewriteParser.RelationRefContext relationRefContext = ctx.relationRef().get(0);
            switch (relationRefContext.ref.getType()) {
                case RewriteParser.STRING:
                    expression.setRelation(unquote(relationRefContext.STRING().getText()));
                    break;

                case RewriteParser.TUPLE_USERSET_RELATION:
                    expression.setRelation(relationRefContext.TUPLE_USERSET_RELATION().getText());
                    break;
            }
        }

        return expression;
    }

    @Override
    public RewriteExpression visitThisUserset(RewriteParser.ThisUsersetContext ctx) {
        return new ThisUsersetExpression();
    }

    @Override
    public RewriteExpression visitTupleToUserset(RewriteParser.TupleToUsersetContext ctx) {
        return new TupleToUsersetExpression(
            (TuplesetExpression) visitTupleset(ctx.tupleset()),
            (ComputedUsersetExpression) visitComputedUserset(ctx.computedUserset()));
    }

    @Override
    public RewriteExpression visitTupleset(RewriteParser.TuplesetContext ctx) {
        TuplesetExpression expression = new TuplesetExpression();

        if (ctx.objectRef().size() > 1)
            throw new RuntimeException("More than one object specified"); //TODO: figure out which exception to throw
        if (!ctx.objectRef().isEmpty()) {
            RewriteParser.ObjectRefContext objectRefContext = ctx.objectRef().get(0);

            switch (objectRefContext.ref.getType()) {
                case RewriteParser.STRING:
                    expression.setObject(unquote(objectRefContext.STRING().getText()));
                    break;

                case RewriteParser.TUPLE_USERSET_OBJECT:
                    expression.setObject(objectRefContext.TUPLE_USERSET_OBJECT().getText());
                    break;
            }
        }

        if (ctx.relationRef().size() > 1)
            throw new RuntimeException("More than one relation specified"); //TODO: figure out which exception to throw
        if (!ctx.relationRef().isEmpty()) {
            RewriteParser.RelationRefContext relationRefContext = ctx.relationRef().get(0);
            switch (relationRefContext.ref.getType()) {
                case RewriteParser.STRING:
                    expression.setRelation(unquote(relationRefContext.STRING().getText()));
                    break;

                case RewriteParser.TUPLE_USERSET_RELATION:
                    expression.setRelation(relationRefContext.TUPLE_USERSET_RELATION().getText());
                    break;
            }
        }

        return expression;
    }

    @Override
    public RewriteExpression visitSetOperationUserset(RewriteParser.SetOperationUsersetContext ctx) {
        return super.visitSetOperationUserset(ctx);
    }

    @Override
    public RewriteExpression visitUnionUserset(RewriteParser.UnionUsersetContext ctx) {
        UnionUsersetExpression expression = new UnionUsersetExpression();

        for (RewriteParser.UsersetContext i : ctx.userset()) {
            expression.getChildren().add(visitUserset(i));
        }

        return expression;
    }

    @Override
    public RewriteExpression visitIntersectUserset(RewriteParser.IntersectUsersetContext ctx) {
        IntersectUsersetExpression expression = new IntersectUsersetExpression();

        for (RewriteParser.UsersetContext i : ctx.userset()) {
            expression.getChildren().add(visitUserset(i));
        }

        return expression;
    }

    @Override
    public RewriteExpression visitExcludeUserset(RewriteParser.ExcludeUsersetContext ctx) {
        ExcludeUsersetExpression expression = new ExcludeUsersetExpression();

        for (RewriteParser.UsersetContext i : ctx.userset()) {
            expression.getChildren().add(visitUserset(i));
        }

        return expression;
    }

    @Override
    public RewriteExpression visitObjectRef(RewriteParser.ObjectRefContext ctx) {
        return super.visitObjectRef(ctx);
    }

    @Override
    public RewriteExpression visitRelationRef(RewriteParser.RelationRefContext ctx) {
        return super.visitRelationRef(ctx);
    }

    private String unquote(String value) {
        return value.substring(1, value.length() - 1);
    }
}
