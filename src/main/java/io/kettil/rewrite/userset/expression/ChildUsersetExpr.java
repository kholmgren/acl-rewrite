package io.kettil.rewrite.userset.expression;

import lombok.Value;

@Value
public class ChildUsersetExpr extends UsersetExpr {
    Context context;
    UsersetExpr usersetExpr;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitChildUsersetExpr(this);
    }
}
