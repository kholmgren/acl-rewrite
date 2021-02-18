package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type", "object", "relation"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TuplesetExpr extends UsersetExpr {
    String object;
    String relation;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitTuplesetExpr(this);
    }
}
