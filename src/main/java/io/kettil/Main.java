package io.kettil;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kettil.ast.Namespace;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        String doc = new String(Files.readAllBytes(Path.of(Main.class.getClassLoader().getResource("rule.txt").getFile())));

        CodePointCharStream input = CharStreams.fromString(doc);

        RewriteParser parser = new RewriteParser(new CommonTokenStream(new RewriteLexer(input)));
        ParseTree tree = parser.namespace();

        RewriteExpressionVisitor visitor = new RewriteExpressionVisitor();
        Namespace namespace = (Namespace) visitor.visit(tree);

        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(namespace));
    }
}
