package io.kettil.tuple;

import io.kettil.tuple.parser.TupleLexer;
import io.kettil.tuple.parser.TupleParser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tuple {
    private String objectNamespace;

    private String objectId;
    private String relation;

    private String userId;

    private String usersetNamespace;
    private String usersetObjectId;
    private String usersetRelation;

    public Tuple(String objectNamespace, String objectId, String relation, String userId) {
        this.objectNamespace = objectNamespace;
        this.objectId = objectId;
        this.relation = relation;
        this.userId = userId;
    }

    public Tuple(String objectNamespace, String objectId, String relation, String usersetNamespace, String usersetObjectId, String usersetRelation) {
        this.objectNamespace = objectNamespace;
        this.objectId = objectId;
        this.relation = relation;
        this.usersetNamespace = usersetNamespace;
        this.usersetObjectId = usersetObjectId;
        this.usersetRelation = usersetRelation;
    }

    public boolean hasUserId() {
        return userId != null;
    }

    public boolean hasUserset() {
        return !hasUserId();
    }

    @Override
    public String toString() {
        return userId != null
            ? String.format("%s:%s#%s@%s", objectNamespace, objectId, relation, userId)
            : String.format("%s:%s#%s@%s:%s#%s", objectNamespace, objectId, relation, usersetNamespace, usersetObjectId, usersetRelation);
    }

    public static Tuple parse(String value) {
        CharStream input = CharStreams.fromString(value);

        TupleParser parser = new TupleParser(new CommonTokenStream(new TupleLexer(input)));
        ParseTreeWalker walker = new ParseTreeWalker();

        TupleParseListener listener = new TupleParseListener();
        walker.walk(listener, parser.tuple());

        Tuple tuple = new Tuple(
            listener.getObjectNamespace(),
            listener.getObjectId(),
            listener.getRelation(),
            listener.getUserId(),
            listener.getUsersetNamespace(),
            listener.getUsersetObjectId(),
            listener.getUsersetRelation());

        //TODO: validation here or elsewhere?

        return tuple;
    }
}
