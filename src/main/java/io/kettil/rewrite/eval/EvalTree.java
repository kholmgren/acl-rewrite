package io.kettil.rewrite.eval;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.kettil.AclKey;
import io.kettil.rewrite.userset.expression.UsersetExpr;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
@JsonPropertyOrder({"expr", "children", "result"})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EvalTree {
    UsersetExpr expr;
    List<EvalTree> children;
    Set<AclKey> result;
}
