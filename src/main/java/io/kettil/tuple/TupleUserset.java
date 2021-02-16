package io.kettil.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TupleUserset {
    private TupleObject object;
    private String relation;

    public boolean isWild() {
        return object == null || object.isWild() || relation == null;
    }
}
