package io.kettil.tuple;

import io.kettil.tuple.parser.TupleLexer;
import io.kettil.tuple.parser.TupleParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tuple {
    private TupleObject object;
    private String relation;
    private TupleUser user;

    public static boolean isWild(Tuple tuple) {
        return tuple.object == null
            || tuple.object.isWild()
            || tuple.relation == null
            || tuple.user == null
            || tuple.user.isWild();
    }

    public static Tuple parse(String value) {
        CharStream input = CharStreams.fromString(value);

        TupleParser parser = new TupleParser(new CommonTokenStream(new TupleLexer(input)));
        ParseTreeWalker walker = new ParseTreeWalker();

        TupleParseListener listener = new TupleParseListener();
        walker.walk(listener, parser.tuple());

        Tuple tuple = listener.getTuple();

        if (tuple.object == null || tuple.object.isWild() || tuple.relation == null)
            throw new IllegalArgumentException("Tuple object or relation may not be null");

        return tuple;
    }
}
