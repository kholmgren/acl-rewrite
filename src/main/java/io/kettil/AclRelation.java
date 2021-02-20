package io.kettil;

import lombok.Value;

import java.util.Comparator;
import java.util.Objects;

@Value
public class AclRelation implements Comparable<AclRelation> {
    AclKey object;
    AclKey user;

    public AclRelation(AclKey object, AclKey user) {
        this.object = object;
        this.user = user;
    }

    public AclRelation(String objectNamespace, String objectId, String relation, String userId) {
        object = new AclKey(objectNamespace, objectId, relation);
        user = new AclKey(null, userId, null);
    }

    public AclRelation(String objectNamespace, String objectId, String relation, String usersetNamespace, String usersetObjectId, String usersetRelation) {
        object = new AclKey(objectNamespace, objectId, relation);
        user = new AclKey(usersetNamespace, usersetObjectId, usersetRelation);
    }

    public static AclRelation parse(String value) {
        //TODO: validation & error handling

        String[] split = value.split("@");

        AclKey object = AclKey.parse(split[0]);
        if (object.getNamespace() == null || object.getId() == null || object.getRelation() == null)
            throw new IllegalArgumentException("Invalid relation string: " + value);

        AclKey user = AclKey.parse(split[1]);

        boolean isUserId = user.getNamespace() == null && user.getId() != null && user.getRelation() == null;
        boolean isUserset = user.getNamespace() != null && user.getId() != null && user.getRelation() != null;

        if (isUserId || isUserset)
            return new AclRelation(object, user);

        throw new IllegalArgumentException("Invalid relation string: " + value);
    }

    @Override
    public int compareTo(AclRelation aclTuple) {
        return Objects.compare(this, aclTuple,
            Comparator.nullsFirst(Comparator
                .comparing(AclRelation::getObject))
                .thenComparing(AclRelation::getUser));
    }
}
