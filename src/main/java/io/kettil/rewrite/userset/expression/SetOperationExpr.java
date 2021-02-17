package io.kettil.rewrite.userset.expression;

import lombok.Value;

import java.util.List;

@Value
public class SetOperationExpr extends UsersetExpr {
    public enum Op {
        Union,
        Intersect,
        Exclude
    }

    Op op;
    List<UsersetExpr> children;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitSetOperationExpr(this);
    }
}
