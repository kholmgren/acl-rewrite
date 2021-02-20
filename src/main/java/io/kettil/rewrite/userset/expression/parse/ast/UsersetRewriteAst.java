package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type", "userset"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UsersetRewriteAst extends RewriteAst {
    RewriteAst userset;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitUsersetRewriteAst(this);
    }
}
