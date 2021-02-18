package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type", "userset", "computed_userset"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TupleToUsersetExpr extends UsersetExpr {
    TuplesetExpr tupleset;
    @JsonProperty("computed_userset")
    ComputedUsersetExpr computedUserset;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitTupleToUsersetExpr(this);
    }
}
