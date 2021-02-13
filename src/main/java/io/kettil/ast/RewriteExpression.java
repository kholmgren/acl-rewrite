package io.kettil.ast;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ChildUsersetExpression.class, name = "child"),
    @JsonSubTypes.Type(value = ComputedUsersetExpression.class, name = "computed_userset"),
    @JsonSubTypes.Type(value = ExcludeUsersetExpression.class, name = "minus"),
    @JsonSubTypes.Type(value = IntersectUsersetExpression.class, name = "intersect"),
    @JsonSubTypes.Type(value = Namespace.class, name = "namespace"),
    @JsonSubTypes.Type(value = Relation.class, name = "relation"),
    @JsonSubTypes.Type(value = ThisUsersetExpression.class, name = "_this"),
    @JsonSubTypes.Type(value = TuplesetExpression.class, name = "tupleset"),
    @JsonSubTypes.Type(value = TupleToUsersetExpression.class, name = "tuple_to_userset"),
    @JsonSubTypes.Type(value = UnionUsersetExpression.class, name = "union"),
    @JsonSubTypes.Type(value = UsersetRewriteExpression.class, name = "userset_rewrite")
})
public abstract class RewriteExpression {

}
