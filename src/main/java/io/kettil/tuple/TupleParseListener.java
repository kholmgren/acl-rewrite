package io.kettil.tuple;

import io.kettil.tuple.parser.TupleBaseListener;
import io.kettil.tuple.parser.TupleParser;
import lombok.Data;

@Data
class TupleParseListener extends TupleBaseListener {
    private String objectNamespace;
    private String objectId;
    private String relation;

    private String userId;

    private String usersetNamespace;
    private String usersetObjectId;
    private String usersetRelation;

    @Override
    public void exitTuple(TupleParser.TupleContext ctx) {
        objectNamespace = ctx.object().namespace().getText();
        objectId = ctx.object().objectId().getText();
        relation = ctx.relation().getText();
    }

    @Override
    public void exitUser(TupleParser.UserContext ctx) {
        if (ctx.userId() != null)
            userId = ctx.userId().getText();
        else {
            usersetNamespace = ctx.userset().object().namespace().getText();
            usersetObjectId = ctx.userset().object().objectId().getText();
            usersetRelation = ctx.userset().relation().getText();
        }
    }
}
