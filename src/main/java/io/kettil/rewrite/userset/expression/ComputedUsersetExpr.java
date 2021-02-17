package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComputedUsersetExpr extends UsersetExpr {
    String object;
    String relation;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitComputedUsersetExpr(this);
    }
}
