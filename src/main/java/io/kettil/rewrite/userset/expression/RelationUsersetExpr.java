package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

@Value
@JsonPropertyOrder({"@type", "name", "rewrite"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RelationUsersetExpr extends UsersetExpr {
    String name;
    UsersetExpr rewrite;

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitRelationUsersetExpr(this);
    }
}
