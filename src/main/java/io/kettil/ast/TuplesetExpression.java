package io.kettil.ast;

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
public class TuplesetExpression extends RewriteExpression {
    private String object;
    private String relation;
}
