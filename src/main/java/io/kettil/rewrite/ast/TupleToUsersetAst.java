package io.kettil.rewrite.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"@type", "tupleset", "computed_userset"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TupleToUsersetAst extends RewriteAst {
    private TuplesetAst tupleset;
    private ComputedUsersetAst computedUserset;

    @Override
    public <T> T accept(RewriteAstVisitor<T> visitor) {
        return visitor.visitTupleToUsersetAst(this);
    }
}
