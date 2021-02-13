package io.kettil.tuple;

import io.kettil.tuple.parser.TupleBaseListener;
import io.kettil.tuple.parser.TupleParser;

class TupleParseListener extends TupleBaseListener {
    //    private TupleObject object;
//    private String relation;
    private TupleUserSet userSet;
    private TupleUser user;

    Tuple getTuple() {
//        return new Tuple(
//            object,
//            relation,
//            user != null
//                ? user
//                : new TupleUser(null, new TupleUserSet(new TupleObject(), null)));

        return new Tuple(
            userSet,
            user != null
                ? user
                : new TupleUser(null, new TupleUserSet(new TupleObject(), null)));

    }

    @Override
    public void exitTuple(TupleParser.TupleContext ctx) {
//        this.relation = ctx.relation() != null ? ctx.relation().getText() : null;
//        this.object = new TupleObject(
//            ctx.object().namespace() != null ? ctx.object().namespace().getText() : null,
//            ctx.object().objectId() != null ? ctx.object().objectId().getText() : null);

        this.userSet = new TupleUserSet(
            new TupleObject(
                ctx.object().namespace() != null ? ctx.object().namespace().getText() : null,
                ctx.object().objectId() != null ? ctx.object().objectId().getText() : null),
            ctx.relation() != null ? ctx.relation().getText() : null);

        super.exitTuple(ctx);
    }

    @Override
    public void exitUser(TupleParser.UserContext ctx) {
        String userId = null;
        TupleUserSet userSet = null;

        if (ctx.userId() != null)
            userId = ctx.userId().getText();
        else {
            String namespace = null;
            String objectId = null;
            String relation = null;

            if (ctx.userset().object() != null) {
                namespace = ctx.userset().object().namespace().getText();
                objectId = ctx.userset().object().objectId().getText();
            }

            if (ctx.userset().relation() != null)
                relation = ctx.userset().relation().getText();

            userSet = new TupleUserSet(new TupleObject(namespace, objectId), relation);
        }

        user = new TupleUser(userId, userSet);

        super.exitUser(ctx);
    }
}
