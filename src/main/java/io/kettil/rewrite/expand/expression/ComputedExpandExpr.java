package io.kettil.rewrite.expand.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.Set;

@Value
@JsonPropertyOrder({"@type", "object", "relation"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ComputedExpandExpr extends ExpandExpr {
    String object;
    String relation;

    ExpandExpr result;

    @Override
    public Set<String> users() {
        return result.users();
    }

    @Override
    public <T> T accept(ExpandExprVisitor<T> visitor) {
        return visitor.visitComputedExpandExpr(this);
    }
}
