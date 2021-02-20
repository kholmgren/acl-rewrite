//package io.kettil.rewrite.expand.expression;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import io.kettil.tuple.Tuple;
//import lombok.Value;
//
//import java.util.Set;
//
//@Value
//@JsonPropertyOrder({"@type", "object", "relation", "tuples"})
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//public class TuplesetExpandExpr extends ExpandExpr {
//    String object;
//    String relation;
//
//    Set<Tuple> tuples;
//
//    @Override
//    public Set<String> users() {
//        throw new RuntimeException("Not implemented in TuplesetExpandExpr"); //TODO: ugly
//    }
//
//    @Override
//    public <T> T accept(ExpandExprVisitor<T> visitor) {
//        return visitor.visitTuplesetExpandExpr(this);
//    }
//}
