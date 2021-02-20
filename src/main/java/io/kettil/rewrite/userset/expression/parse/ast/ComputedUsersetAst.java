package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type", "namespace", "object", "relation"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ComputedUsersetAst extends RewriteAst {
    String namespace;
    String object;
    String relation;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitComputedUsersetAst(this);
    }
}
