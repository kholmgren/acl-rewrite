package io.kettil.rewrite.ast;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kettil.rewrite.parser.UsersetRewriteLexer;
import io.kettil.rewrite.parser.UsersetRewriteParser;
import lombok.SneakyThrows;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.InputStream;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ChildUsersetAst.class, name = "child"),
    @JsonSubTypes.Type(value = ComputedUsersetAst.class, name = "computed_userset"),
    @JsonSubTypes.Type(value = ExcludeUsersetAst.class, name = "exclude"),
    @JsonSubTypes.Type(value = IntersectUsersetAst.class, name = "intersect"),
    @JsonSubTypes.Type(value = NamespaceAst.class, name = "namespace"),
    @JsonSubTypes.Type(value = RelationAst.class, name = "relation"),
    @JsonSubTypes.Type(value = ThisUsersetAst.class, name = "_this"),
    @JsonSubTypes.Type(value = TuplesetAst.class, name = "tupleset"),
    @JsonSubTypes.Type(value = TupleToUsersetAst.class, name = "tuple_to_userset"),
    @JsonSubTypes.Type(value = UnionUsersetAst.class, name = "union"),
    @JsonSubTypes.Type(value = UsersetRewriteAst.class, name = "userset_rewrite")
})
public abstract class RewriteAst {
    public abstract <T> T accept(RewriteAstVisitor<T> visitor);

    public static NamespaceAst parse(String text) {
        return parse(CharStreams.fromString(text));
    }

    @SneakyThrows
    public static NamespaceAst parse(InputStream inputStream) {
        return parse(CharStreams.fromStream(inputStream));
    }

    private static NamespaceAst parse(CharStream input) {
        UsersetRewriteParser parser = new UsersetRewriteParser(new CommonTokenStream(new UsersetRewriteLexer(input)));

        RewriteAstBuilder visitor = new RewriteAstBuilder();
        return (NamespaceAst) visitor.visit(parser.namespace());
    }
}
