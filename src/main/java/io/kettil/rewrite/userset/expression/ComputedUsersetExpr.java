package io.kettil.rewrite.userset.expression;

import lombok.Value;

@Value
public class ComputedUsersetExpr extends UsersetExpr {
    Context context;

    String object;
    String relation;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitComputedUsersetExpr(this);
    }
}
