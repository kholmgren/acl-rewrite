package io.kettil.rewrite.userset;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kettil.rewrite.userset.expression.NamespaceExpr;
import org.junit.Test;

public class UsersetExprFactoryTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    @Test
    public void build() throws Exception {
//        NamespaceExpr expr = UsersetExprFactory.parse(getClass().getClassLoader().getResourceAsStream("rewrite_rule_build_test.txt"));
//
////        TupleUserset userSet = new TupleUserset(new TupleObject("doc", "readme"), "viewer");
//
//
//        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(expr));
    }
}