package io.kettil.ast;

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
public class TupleToUsersetExpression extends RewriteExpression {
    private TuplesetExpression tupleset;
    private ComputedUsersetExpression computedUserset;
}
