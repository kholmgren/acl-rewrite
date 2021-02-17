package io.kettil.rewrite.check;

import io.kettil.rewrite.userset.expression.parse.ast.*;
import io.kettil.rewrite.check.expression.CheckExpr;
import io.kettil.tuple.TupleUserset;

public class CheckExpressionBuilder implements RewriteAstVisitor<CheckExpr> {
    public static CheckExpr build(TupleUserset userSet) {
        if (userSet.isWild())
            throw new IllegalArgumentException("Wildcard userSet is not allowed");

        return null;
    }

    @Override
    public CheckExpr visitNamespaceAst(NamespaceAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitRelationAst(RelationAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitUsersetRewriteAst(UsersetRewriteAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitChildUsersetAst(ChildUsersetAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitComputedUsersetAst(ComputedUsersetAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitThisUsersetAst(ThisUsersetAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitTupleToUsersetAst(TupleToUsersetAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitTuplesetAst(TuplesetAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitUnionUsersetAst(UnionUsersetAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitIntersectUsersetAst(IntersectUsersetAst ast) {
        return null;
    }

    @Override
    public CheckExpr visitExcludeUsersetAst(ExcludeUsersetAst ast) {
        return null;
    }
}
