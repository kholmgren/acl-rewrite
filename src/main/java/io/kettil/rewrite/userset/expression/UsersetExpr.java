package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ChildUsersetExpr.class, name = "child"),
    @JsonSubTypes.Type(value = ComputedUsersetExpr.class, name = "computed_userset"),
    @JsonSubTypes.Type(value = NamespaceUsersetExpr.class, name = "namespace"),
    @JsonSubTypes.Type(value = RelationUsersetExpr.class, name = "relation"),
    @JsonSubTypes.Type(value = SetOperationUsersetExpr.class, name = "set_operation"),
    @JsonSubTypes.Type(value = ThisUsersetExpr.class, name = "this"),
    @JsonSubTypes.Type(value = TuplesetExpr.class, name = "tupleset"),
    @JsonSubTypes.Type(value = TupleToUsersetExpr.class, name = "tuple_to_userset"),
})
public abstract class UsersetExpr {
    public abstract <T> T accept(Visitor<T> visitor);

    public interface Visitor<T> {
        T visitChildUsersetExpr(ChildUsersetExpr expr);

        T visitComputedUsersetExpr(ComputedUsersetExpr expr);

        T visitNamespaceUsersetExpr(NamespaceUsersetExpr expr);

        T visitRelationUsersetExpr(RelationUsersetExpr expr);

        T visitSetOperationExpr(SetOperationUsersetExpr expr);

        T visitThisUsersetExpr(ThisUsersetExpr expr);

        T visitTuplesetExpr(TuplesetExpr expr);

        T visitTupleToUsersetExpr(TupleToUsersetExpr expr);
    }
}
