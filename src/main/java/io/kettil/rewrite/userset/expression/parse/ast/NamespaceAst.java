package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.util.List;

@Value
@JsonPropertyOrder({"@type", "name", "relations"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class NamespaceAst extends RewriteAst {
    String name;
    List<RelationAst> relations;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitNamespaceAst(this);
    }
}
