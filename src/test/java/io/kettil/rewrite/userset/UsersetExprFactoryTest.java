package io.kettil.rewrite.userset;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kettil.rewrite.userset.expression.NamespaceUsersetExpr;
import org.junit.Test;

public class UsersetExprFactoryTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    @Test
    public void build() throws Exception {
        NamespaceUsersetExpr namespaceConfig = UsersetExprFactory.parse(getClass().getClassLoader().getResourceAsStream("rewrite_rule_build_test.txt"));


        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(namespaceConfig));
    }
}