package io.kettil;

import lombok.Data;

import java.time.Instant;

//TBD: Here to demonstrate some form of meta for the relation, like a zookie, audit, etc

@Data
public class AclRelationMeta {
    private Instant created = Instant.now();
}
