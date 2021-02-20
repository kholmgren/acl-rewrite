package io.kettil;

import io.kettil.rewrite.userset.expression.NamespaceUsersetExpr;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class AclService {
    private final Map<String, NamespaceUsersetExpr> namespaces = new HashMap<>();
    private final ConcurrentSkipListMap<AclRelation, AclRelationMeta> relations = new ConcurrentSkipListMap<>();

    @SneakyThrows
    public NamespaceUsersetExpr getNamespaceConfig(String name) {
        return namespaces.get(name);
    }

    @SneakyThrows
    public void putNamespaceConfig(NamespaceUsersetExpr namespaceExpr) {
        namespaces.put(namespaceExpr.getName(), namespaceExpr);
    }

    public void removeNamespaceConfig(String name) {
        namespaces.remove(name);
    }

    public Set<AclRelation> getRelations(AclKey object) {
        Set<AclRelation> ret = new TreeSet<>();

        ConcurrentNavigableMap<AclRelation, AclRelationMeta> tailMap = relations.tailMap(new AclRelation(object, null));

        for (Map.Entry<AclRelation, AclRelationMeta> e : tailMap.entrySet()) {
            if (!object.match(e.getKey().getObject()))
                break;

            ret.add(e.getKey());
        }

        return ret;
    }

    public void addRelation(AclRelation relation) {
        relations.put(relation, new AclRelationMeta());
    }

    public void removeRelation(AclRelation relation) {
        relations.remove(relation);
    }

//    public Set<String> getUserIds(AclKey object) {
//        Stack<AclTuple> relations = new Stack<>();
//        relations.addAll(getRelations(object));
//
//        Set<String> userIds = new TreeSet<>();
//
//        while (!relations.empty()) {
//            AclKey user = relations.pop().getUser();
//            if (user.idOnly())
//                userIds.add(user.getId());
//            else
//                relations.addAll(getRelations(user));
//        }
//
//        return userIds;
//    }
}
