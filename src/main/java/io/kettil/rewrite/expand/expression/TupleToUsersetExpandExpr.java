//package io.kettil.rewrite.expand.expression;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import lombok.Value;
//
//import java.util.Set;
//
//@Value
//@JsonPropertyOrder({"@type", "tupleset", "computed_userset"})
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//public class TupleToUsersetExpandExpr extends ExpandExpr {
//    TuplesetExpandExpr tupleset;
//    @JsonProperty("computed_userset")
//    ComputedExpandExpr computedUserset;
//
//    @Override
//    public Set<String> users() {
//        return computedUserset.users();
//    }
//
//    @Override
//    public <T> T accept(ExpandExprVisitor<T> visitor) {
//        return visitor.visitTupleToUsersetExpandExpr(this);
//    }
//}
