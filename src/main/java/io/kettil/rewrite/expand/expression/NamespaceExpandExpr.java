package io.kettil.rewrite.expand.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.Map;
import java.util.Set;

@Value
@JsonPropertyOrder({"@type", "name", "relations"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class NamespaceExpandExpr extends ExpandExpr {
    String name;
    Map<String, ExpandExpr> relations;

    @Override
    public Set<String> users() {
        throw new RuntimeException("Not implemented in NamespaceExpandExpr"); //TODO: ugly
    }

    @Override
    public <T> T accept(ExpandExprVisitor<T> visitor) {
        return visitor.visitNamespaceExpandExpr(this);
    }
}
