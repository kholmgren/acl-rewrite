package io.kettil.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonPropertyOrder({"@type", "children"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UnionUsersetExpression extends RewriteExpression {
    private final List<RewriteExpression> children = new ArrayList<>();
}
