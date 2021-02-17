package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"@type", "userset"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ChildUsersetAst extends RewriteAst {
    private RewriteAst userset;

    @Override
    public <T> T accept(RewriteAstVisitor<T> visitor) {
        return visitor.visitChildUsersetAst(this);
    }
}