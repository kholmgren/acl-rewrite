package io.kettil.rewrite.userset.expression;

import lombok.Value;

import java.util.Map;

@Value
public class NamespaceExpr extends UsersetExpr {
    String name;
    Map<String, UsersetExpr> relations;

    @Override
    public <T> T accept(UsersetExprVisitor<T> visitor) {
        return visitor.visitNamespaceExpr(this);
    }
}
