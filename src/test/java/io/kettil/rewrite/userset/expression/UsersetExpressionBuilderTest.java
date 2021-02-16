package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kettil.rewrite.ast.NamespaceAst;
import io.kettil.rewrite.ast.RewriteAst;
import io.kettil.rewrite.userset.expression.UsersetExpr;
import io.kettil.rewrite.userset.expression.UsersetExpressionBuilder;
import org.junit.Test;

public class UsersetExpressionBuilderTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    @Test
    public void build() throws Exception {
        NamespaceAst namespace = RewriteAst.parse(getClass().getClassLoader().getResourceAsStream("rewrite_rule_build_test.txt"));

//        TupleUserset userSet = new TupleUserset(new TupleObject("doc", "readme"), "viewer");

        UsersetExpr expr = UsersetExpressionBuilder.build(namespace);

        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(expr));
    }
}