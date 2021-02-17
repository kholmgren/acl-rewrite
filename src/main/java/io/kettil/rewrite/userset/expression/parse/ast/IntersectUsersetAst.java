//package io.kettil.rewrite.userset.expression.parse.ast;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@JsonPropertyOrder({"@type", "children"})
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
//public class IntersectUsersetAst extends RewriteAst {
//    private final List<RewriteAst> children = new ArrayList<>();
//
//    @Override
//    public <T> T accept(RewriteAstVisitor<T> visitor) {
//        return visitor.visitIntersectUsersetAst(this);
//    }
//}