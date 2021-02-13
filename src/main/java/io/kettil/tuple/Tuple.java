package io.kettil.tuple;

import io.kettil.tuple.parser.TupleBaseListener;
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
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Obj {
        private String namespace;
        private String objectId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsrSet {
        private Obj objectSet;
        private String relation;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usr {
        private String userId;
        private UsrSet userSet;
    }

    private Obj object;
    private String relation;
    private Usr user;

    private static class TupleListener extends TupleBaseListener {
        private Obj object;
        private String relation;
        private Usr user;

        public Tuple getTuple() {
            return new Tuple(object, relation, user);
        }

        @Override
        public void exitTuple(TupleParser.TupleContext ctx) {
            object = new Obj(ctx.object().namespace().getText(), ctx.object().objectId().getText());
            relation = ctx.relation().getText();
            super.exitTuple(ctx);
        }

        @Override
        public void exitUser(TupleParser.UserContext ctx) {
            user = ctx.userId() != null
                ? new Usr(ctx.userId().getText(), null)
                : new Usr(null, new UsrSet(
                new Obj(
                    ctx.userset().object().namespace().getText(),
                    ctx.userset().object().objectId().getText()),
                ctx.userset().relation().getText()));

            super.exitUser(ctx);
        }
    }

    public static Tuple parse(String value) {
        CharStream input = CharStreams.fromString(value);

        TupleParser parser = new TupleParser(new CommonTokenStream(new TupleLexer(input)));
        ParseTreeWalker walker = new ParseTreeWalker();

        TupleListener listener = new TupleListener();
        walker.walk(listener, parser.tuple());

        return listener.getTuple();
    }
}
