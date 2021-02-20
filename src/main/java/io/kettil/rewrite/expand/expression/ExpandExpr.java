//package io.kettil.rewrite.expand.expression;
//
//import com.fasterxml.jackson.annotation.JsonSubTypes;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
//
//import java.util.Set;
//
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
//@JsonSubTypes({
//    @JsonSubTypes.Type(value = ChildExpandExpr.class, name = "child"),
//    @JsonSubTypes.Type(value = ComputedExpandExpr.class, name = "computed_userset"),
//    @JsonSubTypes.Type(value = NamespaceExpandExpr.class, name = "namespace"),
//    @JsonSubTypes.Type(value = SetOperationExpandExpr.class, name = "set_operation"),
//    @JsonSubTypes.Type(value = ThisExpandExpr.class, name = "this"),
//    @JsonSubTypes.Type(value = TuplesetExpandExpr.class, name = "tupleset"),
//    @JsonSubTypes.Type(value = TupleToUsersetExpandExpr.class, name = "tuple_to_userset"),
//})
//public abstract class ExpandExpr {
//    public abstract Set<String> users();
//
//    public abstract <T> T accept(ExpandExprVisitor<T> visitor);
//}
