package io.kettil;

import io.kettil.rewrite.userset.expression.NamespaceUsersetExpr;
import io.kettil.tuple.Tuple;

import java.util.Set;

public interface AclRepo {
    NamespaceUsersetExpr getNamespaceConfig(String name);

    void saveNamespaceConfig(NamespaceUsersetExpr namespaceExpr);

    void deleteNamespaceConfig(String name);

    void saveTuple(Tuple tuple);

    void deleteTuple(Tuple tuple);

//    List<Tuple> getTuplesByObjectId(String namespace, String objectId);

    Set<String> getUsersByObjectIdAndRelation(String namespace, String objectId, String relation);

    Set<Tuple> getTuplesByObjectIdAndRelation(String namespace, String objectId, String relation);
}
