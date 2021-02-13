package io.kettil.rewrite.ast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"@type", "name", "relations"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class NamespaceAst extends RewriteAst {
    private String name;
    private final List<RelationAst> relations = new ArrayList<>();

    @Override
    public <T> T visit(RewriteAstVisitor<T> visitor) {
        return visitor.visitNamespaceAst(this);
    }
}
