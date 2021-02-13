package io.kettil.rewrite.parser.ast;

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
public class TupleToUsersetAst extends AbstractRewriteAst {
    private TuplesetAst tupleset;
    private ComputedUsersetAst computedUserset;
}
