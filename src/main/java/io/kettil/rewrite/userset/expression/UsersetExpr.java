package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ChildUsersetExpr.class, name = "child"),
    @JsonSubTypes.Type(value = ComputedUsersetExpr.class, name = "computed_userset"),
    @JsonSubTypes.Type(value = NamespaceUsersetExpr.class, name = "namespace"),
    @JsonSubTypes.Type(value = SetOperationUsersetExpr.class, name = "set_operation"),
    @JsonSubTypes.Type(value = ThisUsersetExpr.class, name = "this"),
    @JsonSubTypes.Type(value = TuplesetExpr.class, name = "tupleset"),
    @JsonSubTypes.Type(value = TupleToUsersetExpr.class, name = "tuple_to_userset"),
})
public abstract class UsersetExpr {
    public abstract <T> T accept(UsersetExprVisitor<T> visitor);
}
