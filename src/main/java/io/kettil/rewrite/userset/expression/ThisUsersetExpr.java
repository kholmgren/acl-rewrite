package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ThisUsersetExpr extends UsersetExpr {
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitThisUsersetExpr(this);
    }
}
