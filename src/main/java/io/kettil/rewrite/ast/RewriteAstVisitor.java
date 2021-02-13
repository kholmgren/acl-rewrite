package io.kettil.rewrite.ast;

public interface RewriteAstVisitor<T> {

    T visitNamespaceAst(NamespaceAst ast);

    T visitRelationAst(RelationAst ast);

    T visitUsersetRewriteAst(UsersetRewriteAst ast);

    T visitChildUsersetAst(ChildUsersetAst ast);

    T visitComputedUsersetAst(ComputedUsersetAst ast);

    T visitThisUsersetAst(ThisUsersetAst ast);

    T visitTupleToUsersetAst(TupleToUsersetAst ast);

    T visitTuplesetAst(TuplesetAst ast);

    T visitUnionUsersetAst(UnionUsersetAst ast);

    T visitIntersectUsersetAst(IntersectUsersetAst ast);

    T visitExcludeUsersetAst(ExcludeUsersetAst ast);
}