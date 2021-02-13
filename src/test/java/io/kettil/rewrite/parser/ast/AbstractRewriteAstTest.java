package io.kettil.rewrite.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractRewriteAstTest {
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    @Test
    public void parse() throws Exception {
        JsonNode expected = MAPPER.readTree(getClass().getClassLoader().getResourceAsStream("rewrite_rule_expected.json"));

        NamespaceAst namespace = AbstractRewriteAst.parse(getClass().getClassLoader().getResourceAsStream("rewrite_rule.txt"));
        JsonNode actual = MAPPER.convertValue(namespace, JsonNode.class);

        assertEquals(expected, actual);
    }
}