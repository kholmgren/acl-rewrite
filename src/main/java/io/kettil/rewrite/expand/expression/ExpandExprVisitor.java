package io.kettil.rewrite.expand.expression;

public interface ExpandExprVisitor<T> {
    T visitNamespaceExpandExpr(NamespaceExpandExpr expr);

    T visitChildExpandExpr(ChildExpandExpr expr);

    T visitComputedExpandExpr(ComputedExpandExpr expr);

    T visitThisExpandExpr(ThisExpandExpr expr);

    T visitSetOperationExpandExpr(SetOperationExpandExpr setOperationExpr);

    T visitTuplesetExpandExpr(TuplesetExpandExpr expr);

    T visitTupleToUsersetExpandExpr(TupleToUsersetExpandExpr expr);
}
