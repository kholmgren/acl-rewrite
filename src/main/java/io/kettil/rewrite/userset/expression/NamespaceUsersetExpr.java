package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.Map;

@Value
@JsonPropertyOrder({"@type", "name", "relations"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class NamespaceUsersetExpr extends UsersetExpr {
    String name;
    Map<String, UsersetExpr> relations;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitNamespaceExpr(this);
    }
}
