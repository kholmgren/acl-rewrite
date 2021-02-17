package io.kettil.rewrite.userset.expression.parse.ast;

public interface RewriteAstVisitor<T> {

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
