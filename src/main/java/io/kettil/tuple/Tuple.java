package io.kettil.tuple;

import io.kettil.tuple.parser.TupleLexer;
import io.kettil.tuple.parser.TupleParser;
import lombok.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tuple {
    public static final String ANY = "*";

    @NonNull
    private TupleObject object;
    @NonNull
    private String relation;
    @NonNull
    private TupleUser user;

    @Override
    public String toString() {
        return String.format("%s#%s@%s", object, relation, user);
    }

    public static Tuple parse(String value) {
        CharStream input = CharStreams.fromString(value);

        TupleParser parser = new TupleParser(new CommonTokenStream(new TupleLexer(input)));
        ParseTreeWalker walker = new ParseTreeWalker();

        TupleParseListener listener = new TupleParseListener();
        walker.walk(listener, parser.tuple());

        Tuple tuple = listener.getTuple();

        //TODO: validation here or elsewhere?

        return tuple;
    }
}
