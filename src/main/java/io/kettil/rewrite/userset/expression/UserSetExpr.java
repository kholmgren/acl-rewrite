package io.kettil.rewrite.userset.expression;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
@JsonSubTypes({
//    @JsonSubTypes.Type(value = ChildUsersetAst.class, name = "child"),
})
public abstract class UserSetExpr {

}
