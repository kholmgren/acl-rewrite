package io.kettil.rewrite.userset.expression;

import lombok.Value;

import java.util.List;

@Value
public class IntersectUsersetExpr extends UsersetExpr {
    Context context;
    List<UsersetExpr> children;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitIntersectUsersetExpr(this);
    }
}
