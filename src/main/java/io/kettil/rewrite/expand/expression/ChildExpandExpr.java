//package io.kettil.rewrite.expand.expression;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import lombok.Value;
//
//import java.util.Set;
//
//@Value
//@JsonPropertyOrder({"@type", "userset", "users"})
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//public class ChildExpandExpr extends ExpandExpr {
//    ExpandExpr userset;
//    Set<String> users;
//
//    @Override
//    public Set<String> users() {
//        return users;
//    }
//
//    @Override
//    public <T> T accept(ExpandExprVisitor<T> visitor) {
//        return visitor.visitChildExpandExpr(this);
//    }
//}
