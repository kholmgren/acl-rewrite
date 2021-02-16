package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Value;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ChildUsersetExpr.class, name = "child"),
    @JsonSubTypes.Type(value = ComputedUsersetExpr.class, name = "computed_userset"),
    @JsonSubTypes.Type(value = ExcludeUsersetExpr.class, name = "exclude"),
    @JsonSubTypes.Type(value = IntersectUsersetExpr.class, name = "intersect"),
    @JsonSubTypes.Type(value = NamespaceExpr.class, name = "namespace"),
    @JsonSubTypes.Type(value = ThisUsersetExpr.class, name = "this"),
    @JsonSubTypes.Type(value = TuplesetExpr.class, name = "tupleset"),
    @JsonSubTypes.Type(value = TupleToUsersetExpr.class, name = "tuple_to_userset"),
    @JsonSubTypes.Type(value = UnionUsersetExpr.class, name = "union"),
})
public abstract class UsersetExpr {
    @Value
    public static class Context {
        String namespace;
        String relation;
    }

//    protected final Context context;
//
//    protected UsersetExpr(Context context) {
//        this.context = context;
//    }

    public abstract <T> T accept(UsersetExprVisitor<T> visitor);
}
