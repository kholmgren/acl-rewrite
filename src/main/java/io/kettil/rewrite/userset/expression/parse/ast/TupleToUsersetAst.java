package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type", "tupleset", "computed_userset"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TupleToUsersetAst extends RewriteAst {
    TuplesetAst tupleset;

    @JsonProperty("computed_userset")
    ComputedUsersetAst computedUserset;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitTupleToUsersetAst(this);
    }
}
