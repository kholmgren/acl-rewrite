package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonPropertyOrder({"@type", "op", "children"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SetOperationUsersetAst extends RewriteAst {
    public enum Op {
        union,
        intersect,
        exclude
    }

    private Op op;
    private final List<RewriteAst> children = new ArrayList<>();

    @Override
    public <T> T accept(RewriteAstVisitor<T> visitor) {
        return visitor.visitSetOperationUsersetAst(this);
    }
}