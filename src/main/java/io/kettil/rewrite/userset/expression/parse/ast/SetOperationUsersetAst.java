package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.kettil.rewrite.userset.expression.SetOp;
import lombok.Value;

import java.util.List;

@Value
@JsonPropertyOrder({"@type", "op", "children"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SetOperationUsersetAst extends RewriteAst {
    SetOp op;
    List<RewriteAst> children;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitSetOperationUsersetAst(this);
    }
}
