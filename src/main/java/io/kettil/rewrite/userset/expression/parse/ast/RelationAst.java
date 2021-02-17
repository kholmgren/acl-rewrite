package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"@type", "name", "userset_rewrite"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RelationAst extends RewriteAst {
    private String name;

    @JsonProperty("userset_rewrite")
    private UsersetRewriteAst usersetRewrite;

    @Override
    public <T> T accept(RewriteAstVisitor<T> visitor) {
        return visitor.visitRelationAst(this);
    }
}
