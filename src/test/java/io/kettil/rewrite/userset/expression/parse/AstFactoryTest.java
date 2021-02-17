package io.kettil.rewrite.userset.expression.parse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kettil.rewrite.userset.expression.parse.ast.NamespaceAst;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AstFactoryTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    @Test
    public void parse() throws Exception {
        JsonNode expected = MAPPER.readTree(getClass().getClassLoader().getResourceAsStream("rewrite_rule_expected.json"));

        NamespaceAst namespace = AstFactory.parse(getClass().getClassLoader().getResourceAsStream("rewrite_rule.txt"));
        JsonNode actual = MAPPER.convertValue(namespace, JsonNode.class);

        assertEquals(expected, actual);
    }
}