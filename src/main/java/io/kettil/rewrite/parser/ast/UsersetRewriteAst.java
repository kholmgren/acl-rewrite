package io.kettil.rewrite.parser.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"@type", "userset"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UsersetRewriteAst extends AbstractRewriteAst {
    private final AbstractRewriteAst userset;
}
