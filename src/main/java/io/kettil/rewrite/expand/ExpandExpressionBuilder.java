package io.kettil.rewrite.expand;

import io.kettil.rewrite.check.expression.CheckExpr;
import io.kettil.rewrite.expand.expression.ExpandExpr;
import io.kettil.tuple.TupleUserSet;

public class ExpandExpressionBuilder {
    public static ExpandExpr build(TupleUserSet userSet){
        if (userSet.isWild())
            throw new IllegalArgumentException("Wildcard userSet is not allowed");

        return null;
    }
}
