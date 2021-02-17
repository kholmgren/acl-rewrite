package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"@type", "object", "relation"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TuplesetAst extends RewriteAst {
    private String object;
    private String relation;

    @Override
    public <T> T accept(RewriteAstVisitor<T> visitor) {
        return visitor.visitTuplesetAst(this);
    }
}
