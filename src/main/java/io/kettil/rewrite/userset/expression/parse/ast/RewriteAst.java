package io.kettil.rewrite.userset.expression.parse.ast;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ChildUsersetAst.class, name = "child"),
    @JsonSubTypes.Type(value = ComputedUsersetAst.class, name = "computed_userset"),
    @JsonSubTypes.Type(value = NamespaceAst.class, name = "namespace"),
    @JsonSubTypes.Type(value = RelationAst.class, name = "relation"),
    @JsonSubTypes.Type(value = SetOperationUsersetAst.class, name = "set_operation"),
    @JsonSubTypes.Type(value = ThisUsersetAst.class, name = "_this"),
    @JsonSubTypes.Type(value = TuplesetAst.class, name = "tupleset"),
    @JsonSubTypes.Type(value = TupleToUsersetAst.class, name = "tuple_to_userset"),
    @JsonSubTypes.Type(value = UsersetRewriteAst.class, name = "userset_rewrite")
})
public abstract class RewriteAst {
    public abstract <T> T accept(Visitor<T> visitor);

    public interface Visitor<T> {

        T visitNamespaceAst(NamespaceAst ast);

        T visitRelationAst(RelationAst ast);

        T visitUsersetRewriteAst(UsersetRewriteAst ast);

        T visitChildUsersetAst(ChildUsersetAst ast);

        T visitComputedUsersetAst(ComputedUsersetAst ast);

        T visitThisUsersetAst(ThisUsersetAst ast);

        T visitTupleToUsersetAst(TupleToUsersetAst ast);

        T visitTuplesetAst(TuplesetAst ast);

        T visitSetOperationUsersetAst(SetOperationUsersetAst ast);
    }
}
