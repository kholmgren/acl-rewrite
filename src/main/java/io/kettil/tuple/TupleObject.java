package io.kettil.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TupleObject {
    private String namespace;
    private String objectId;

    public boolean isWild() {
        return namespace == null || objectId == null;
    }
}
