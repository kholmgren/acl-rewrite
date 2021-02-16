package io.kettil.rewrite.userset.expression;

import lombok.Value;

@Value
public class ThisUsersetExpr extends UsersetExpr {
    Context context;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitThisUsersetExpr(this);
    }

    public boolean check(String objectId) {
        return false;
    }
}
