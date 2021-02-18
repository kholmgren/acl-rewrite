package io.kettil.rewrite.userset;

import io.kettil.rewrite.userset.expression.NamespaceExpr;
import io.kettil.tuple.Tuple;

import java.util.List;

public interface AclRepo {
    NamespaceExpr getNamespaceConfig(String name);

    void saveNamespaceConfig(NamespaceExpr namespaceExpr);

    void deleteNamespaceConfig(String name);

    List<Tuple> getTuplesByObjectId(String namespace, String objectId);

    List<Tuple> getTuplesByObjectIdAndRelation(String namespace, String objectId, String relation);

    void saveTuple(Tuple tuple);

    void deleteTuple(Tuple tuple);
}
