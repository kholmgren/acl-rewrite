package io.kettil.ast;

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
public class Relation extends RewriteExpression {
    private String name;

    @JsonProperty("userset_rewrite")
    private UsersetRewriteExpression usersetRewrite;
}
