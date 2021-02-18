package io.kettil.rewrite.userset.expression;

public interface UsersetExprVisitor<T> {
    T visitNamespaceExpr(NamespaceUsersetExpr expr);

    T visitChildUsersetExpr(ChildUsersetExpr expr);

    T visitComputedUsersetExpr(ComputedUsersetExpr expr);

    T visitThisUsersetExpr(ThisUsersetExpr expr);

    T visitSetOperationExpr(SetOperationUsersetExpr setOperationExpr);

    T visitTuplesetExpr(TuplesetExpr expr);

    T visitTupleToUsersetExpr(TupleToUsersetExpr expr);
}
