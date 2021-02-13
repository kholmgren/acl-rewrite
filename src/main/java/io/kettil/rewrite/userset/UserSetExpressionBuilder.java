package io.kettil.rewrite.userset;

import io.kettil.rewrite.ast.*;
import io.kettil.rewrite.userset.expression.UserSetExpr;
import io.kettil.tuple.TupleUserSet;

public class UserSetExpressionBuilder implements RewriteAstVisitor<UserSetExpr> {
    public static UserSetExpr build(TupleUserSet userSet) {
        if (userSet.isWild())
            throw new IllegalArgumentException("Wildcard userSet is not allowed");

        return null;
    }

    @Override
    public UserSetExpr visitNamespaceAst(NamespaceAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitRelationAst(RelationAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitUsersetRewriteAst(UsersetRewriteAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitChildUsersetAst(ChildUsersetAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitComputedUsersetAst(ComputedUsersetAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitThisUsersetAst(ThisUsersetAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitTupleToUsersetAst(TupleToUsersetAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitTuplesetAst(TuplesetAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitUnionUsersetAst(UnionUsersetAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitIntersectUsersetAst(IntersectUsersetAst ast) {
        return null;
    }

    @Override
    public UserSetExpr visitExcludeUsersetAst(ExcludeUsersetAst ast) {
        return null;
    }
}
