package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.List;

@Value
@JsonPropertyOrder({"@type", "op", "children"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SetOperationUsersetExpr extends UsersetExpr {
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
