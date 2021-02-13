package io.kettil.rewrite.expand.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
//    @JsonSubTypes.Type(value = ChildUsersetAst.class, name = "child"),
})
public abstract class ExpandExpr {
    public ExpandExpr expand(String userId) {
        return null;
    }

    public void query() {
        //TODO: fill in results by getting the results for each node
    }
}
