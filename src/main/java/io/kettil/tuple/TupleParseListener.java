package io.kettil.tuple;

import io.kettil.tuple.parser.TupleBaseListener;
import io.kettil.tuple.parser.TupleParser;

import static io.kettil.tuple.Tuple.ANY;

class TupleParseListener extends TupleBaseListener {
    private TupleObject object;
    private String relation = ANY;
    private TupleUser user = new TupleUser(ANY, null);

    Tuple getTuple() {
        return new Tuple(object, relation, user);
    }

    @Override
    public void exitTuple(TupleParser.TupleContext ctx) {
        this.object = new TupleObject(
            ctx.object().namespace() != null ? ctx.object().namespace().getText() : ANY,
            ctx.object().objectId() != null ? ctx.object().objectId().getText() : ANY);
        this.relation = ctx.relation() != null ? ctx.relation().getText() : ANY;

        super.exitTuple(ctx);
    }

    @Override
    public void exitUser(TupleParser.UserContext ctx) {
        String userId = null;
        TupleUserset userSet = null;

        if (ctx.userId() != null)
            userId = ctx.userId().getText();
        else {
            String namespace = ANY;
            String objectId = ANY;
            String relation = ANY;

            if (ctx.userset().object() != null) {
                namespace = ctx.userset().object().namespace().getText();
                objectId = ctx.userset().object().objectId().getText();
            }

            if (ctx.userset().relation() != null)
                relation = ctx.userset().relation().getText();

            userSet = new TupleUserset(new TupleObject(namespace, objectId), relation);
        }

        user = new TupleUser(userId, userSet);

        super.exitUser(ctx);
    }
}
