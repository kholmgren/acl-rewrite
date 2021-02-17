package io.kettil.rewrite.userset.expression;

import lombok.Value;

@Value
public class TupleToUsersetExpr extends UsersetExpr {
    TuplesetExpr tuplesetExpr;
    ComputedUsersetExpr computedUsersetExpr;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitTupleToUsersetExpr(this);
    }
}
