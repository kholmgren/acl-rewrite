package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type", "name", "userset_rewrite"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RelationAst extends RewriteAst {
    String name;

    @JsonProperty("userset_rewrite")
    UsersetRewriteAst usersetRewrite;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitRelationAst(this);
    }
}
