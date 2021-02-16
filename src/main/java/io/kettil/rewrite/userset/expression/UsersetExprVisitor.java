package io.kettil.rewrite.userset.expression;

public interface UsersetExprVisitor<T> {
    T visitNamespaceExpr(NamespaceExpr expr);

    T visitChildUsersetExpr(ChildUsersetExpr expr);

    T visitComputedUsersetExpr(ComputedUsersetExpr expr);

    T visitThisUsersetExpr(ThisUsersetExpr expr);

    T visitUnionUsersetExpr(UnionUsersetExpr expr);

    T visitIntersectUsersetExpr(IntersectUsersetExpr expr);

    T visitExcludeUsersetExpr(ExcludeUsersetExpr expr);

    T visitTuplesetExpr(TuplesetExpr expr);

    T visitTupleToUsersetExpr(TupleToUsersetExpr expr);
}
