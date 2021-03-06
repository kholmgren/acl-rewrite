package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type"})
public class ThisUsersetAst extends RewriteAst {

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitThisUsersetAst(this);
    }
}
