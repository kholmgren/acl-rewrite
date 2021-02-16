package io.kettil.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TupleUser {
    private String userId;
    private TupleUserset userSet;

    public boolean isWild() {
        return userId == null && userSet == null || userId == null && userSet.isWild();
    }
}
