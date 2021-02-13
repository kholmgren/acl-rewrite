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
    //    private TupleObject object;
//    private String relation;
    private TupleUserSet userSet;
    private TupleUser user;

//    public boolean isWild() {
//        return object == null
//            || object.isWild()
//            || relation == null
//            || user == null
//            || user.isWild();
//    }

    public boolean isWild() {
        return userSet == null
            || userSet.isWild()
            || user == null
            || user.isWild();
    }

    public static Tuple parse(String value) {
        return parse(value, false);
    }

    public static Tuple parse(String value, boolean allowWild) {
        CharStream input = CharStreams.fromString(value);

        TupleParser parser = new TupleParser(new CommonTokenStream(new TupleLexer(input)));
        ParseTreeWalker walker = new ParseTreeWalker();

        TupleParseListener listener = new TupleParseListener();
        walker.walk(listener, parser.tuple());

        Tuple tuple = listener.getTuple();

        if (!allowWild && tuple.isWild())
            throw new IllegalArgumentException("Wildcard is not allowed");

        return tuple;
    }
}
