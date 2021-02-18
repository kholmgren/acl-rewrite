package io.kettil.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import static io.kettil.tuple.Tuple.ANY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TupleUserset {
    @NonNull
    private TupleObject object;
    @NonNull
    private String relation = ANY;

    @Override
    public String toString() {
        return String.format("%s#%s", object, relation);
    }
}
