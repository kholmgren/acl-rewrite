package io.kettil.rewrite.userset.expression;

import lombok.Value;

import java.util.List;

@Value
public class UnionUsersetExpr extends UsersetExpr {
    Context context;
    List<UsersetExpr> children;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitUnionUsersetExpr(this);
    }
}
