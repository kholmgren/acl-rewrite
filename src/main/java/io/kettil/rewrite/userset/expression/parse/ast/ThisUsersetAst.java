package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"@type"})
public class ThisUsersetAst extends RewriteAst {

    @Override
    public <T> T accept(RewriteAstVisitor<T> visitor) {
        return visitor.visitThisUsersetAst(this);
    }
}
