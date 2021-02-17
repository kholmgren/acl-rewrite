package io.kettil.rewrite.userset.expression;

import lombok.Value;

@Value
public class ThisUsersetExpr extends UsersetExpr {
    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitThisUsersetExpr(this);
    }
}
