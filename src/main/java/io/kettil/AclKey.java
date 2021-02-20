package io.kettil;

import lombok.Value;

import java.util.Comparator;
import java.util.Objects;

@Value
public class AclKey implements Comparable<AclKey> {
    public static final AclKey MIN_KEY = new AclKey(null, null, null);

    String namespace;
    String id;
    String relation;

    public boolean idOnly() {
        return namespace == null && id != null && relation == null;
    }

    public static String format(String namespace, String id, String relation) {
        return String.format("%s:%s#%s",
            namespace,
            id,
            relation);
    }

    public static AclKey parse(String value) {
        //TODO: validation & error handling

        String[] namespaceAndId2Relation = value.split("#");

        if (namespaceAndId2Relation.length == 1)
            return new AclKey(null, value, null);

        String[] namespace2Id = namespaceAndId2Relation[0].split(":");

        return new AclKey(
            namespace2Id[0],
            namespace2Id[1],
            namespaceAndId2Relation[1]);
    }

    @Override
    public int compareTo(AclKey aclKey) {
        return Objects.compare(this, aclKey,
            Comparator.nullsFirst(Comparator
                .comparing(AclKey::getNamespace))
                .thenComparing(AclKey::getId)
                .thenComparing(AclKey::getRelation));
    }

    public boolean match(AclKey aclKey) {
        //Use null as wildcard

        return (namespace == null || namespace.equals(aclKey.namespace)) &&
            (id == null || id.equals(aclKey.id)) &&
            (relation == null || relation.equals(aclKey.relation));
    }
}
