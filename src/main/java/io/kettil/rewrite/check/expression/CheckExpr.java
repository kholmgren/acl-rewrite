package io.kettil.rewrite.check.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
//    @JsonSubTypes.Type(value = ChildUsersetAst.class, name = "child"),
})
public abstract class CheckExpr {
    public boolean check(String userId) {
        return false;
    }
}
