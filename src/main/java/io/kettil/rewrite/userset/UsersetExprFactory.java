package io.kettil.rewrite.userset;

import io.kettil.rewrite.userset.expression.*;
import io.kettil.rewrite.userset.expression.parse.AstFactory;
import io.kettil.rewrite.userset.expression.parse.ast.*;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class UsersetExprFactory {
    public static NamespaceUsersetExpr parse(NamespaceAst ast) {
        return new Visitor().visitNamespaceAst(ast);
    }

    public static NamespaceUsersetExpr parse(String text) {
        return new Visitor().visitNamespaceAst(AstFactory.parse(text));
    }

    @SneakyThrows
    public static NamespaceUsersetExpr parse(InputStream inputStream) {
        return new Visitor().visitNamespaceAst(AstFactory.parse(inputStream));
    }

    private static class Visitor implements RewriteAstVisitor<UsersetExpr> {
        private String namespace;
        private String relation;

        @Override
        public NamespaceUsersetExpr visitNamespaceAst(NamespaceAst ast) {
            namespace = ast.getName();

            Map<String, UsersetExpr> relations = new LinkedHashMap<>();
            for (RelationAst r : ast.getRelations()) {
                UsersetExpr rel = visitRelationAst(r);
                relations.put(relation, rel);
            }

            return new NamespaceUsersetExpr(namespace, relations); //TODO: NamespaceExpr really necessary?
        }

        @Override
        public UsersetExpr visitRelationAst(RelationAst ast) {
            relation = ast.getName();
            return visitUsersetRewriteAst(ast.getUsersetRewrite());
        }

        @Override
        public UsersetExpr visitUsersetRewriteAst(UsersetRewriteAst ast) {
            return ast == null
                ? new ChildUsersetExpr(new ThisUsersetExpr())
                : ast.getUserset().accept(this);
        }

        @Override
        public ChildUsersetExpr visitChildUsersetAst(ChildUsersetAst ast) {
            return new ChildUsersetExpr(ast.getUserset().accept(this));
        }

        @Override
        public ComputedUsersetExpr visitComputedUsersetAst(ComputedUsersetAst ast) {
            return new ComputedUsersetExpr(ast.getObject(), ast.getRelation());
        }

        @Override
        public ThisUsersetExpr visitThisUsersetAst(ThisUsersetAst ast) {
            return new ThisUsersetExpr();
        }

        @Override
        public TupleToUsersetExpr visitTupleToUsersetAst(TupleToUsersetAst ast) {
            return new TupleToUsersetExpr(
                visitTuplesetAst(ast.getTupleset()),
                visitComputedUsersetAst(ast.getComputedUserset()));
        }

        @Override
        public TuplesetExpr visitTuplesetAst(TuplesetAst ast) {
            return new TuplesetExpr(ast.getObject(), ast.getRelation());
        }

        private SetOperationUsersetExpr newSetOperationExpr(SetOperationUsersetExpr.Op operation, List<RewriteAst> children) {
            return new SetOperationUsersetExpr(operation, children.stream()
                .map(i -> i.accept(this))
                .collect(Collectors.toList()));
        }

        @Override
        public UsersetExpr visitSetOperationUsersetAst(SetOperationUsersetAst ast) {
            SetOperationUsersetExpr.Op operation;

            switch (ast.getOp()) {
                case union:
                    operation = SetOperationUsersetExpr.Op.Union;
                    break;
                case intersect:
                    operation = SetOperationUsersetExpr.Op.Intersect;
                    break;
                case exclude:
                    operation = SetOperationUsersetExpr.Op.Exclude;
                    break;
                default:
                    throw new IllegalStateException();
            }

            return newSetOperationExpr(operation, ast.getChildren());
        }
    }
}
