package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"@type", "userset"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UsersetRewriteAst extends RewriteAst {
    private final RewriteAst userset;

    @Override
    public <T> T accept(RewriteAstVisitor<T> visitor) {
        return visitor.visitUsersetRewriteAst(this);
    }
}
