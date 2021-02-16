package io.kettil.rewrite.expand;

import io.kettil.rewrite.expand.expression.ExpandExpr;
import io.kettil.tuple.TupleUserset;

public class ExpandExpressionBuilder {
    public static ExpandExpr build(TupleUserset userSet) {
        if (userSet.isWild())
            throw new IllegalArgumentException("Wildcard userSet is not allowed");

        return null;
    }
}
