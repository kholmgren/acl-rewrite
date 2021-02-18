package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type", "userset"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ChildUsersetExpr extends UsersetExpr {
    UsersetExpr userset;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitChildUsersetExpr(this);
    }
}
