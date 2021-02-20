//package io.kettil.rewrite.expand.expression;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import lombok.Value;
//
//import java.util.List;
//import java.util.Set;
//
//@Value
//@JsonPropertyOrder({"@type", "op", "children", "users"})
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//public class SetOperationExpandExpr extends ExpandExpr {
//    public enum Op {
//        Union,
//        Intersect,
//        Exclude
//    }
//
//    Op op;
//    List<ExpandExpr> children;
//    Set<String> users;
//
//    @Override
//    public Set<String> users() {
//        return users;
//    }
//
//    @Override
//    public <T> T accept(ExpandExprVisitor<T> visitor) {
//        return visitor.visitSetOperationExpandExpr(this);
//    }
//}
