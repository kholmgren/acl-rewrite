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
        MAPPER.readValue(getClass().getClassLoader().getResourceAsStream("rewrite_rule_expected.json"), NamespaceAst.class);
        JsonNode expectedJson = MAPPER.readTree(getClass().getClassLoader().getResourceAsStream("rewrite_rule_expected.json"));

        NamespaceAst actualNamespace = NamespaceAstFactory.parse(getClass().getClassLoader().getResourceAsStream("rewrite_rule.txt"));
        JsonNode actualJson = MAPPER.convertValue(actualNamespace, JsonNode.class);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void deserialize() throws Exception {
        NamespaceAst expectedNamespaceAst = MAPPER.readValue(getClass().getClassLoader().getResourceAsStream("rewrite_rule_expected.json"), NamespaceAst.class);
        NamespaceAst actualNamespaceAst = NamespaceAstFactory.parse(getClass().getClassLoader().getResourceAsStream("rewrite_rule.txt"));

        assertEquals(expectedNamespaceAst, actualNamespaceAst);
    }
}