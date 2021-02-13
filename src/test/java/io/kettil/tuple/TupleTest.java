package io.kettil.tuple;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TupleTest {
    @Test
    public void parse() {
        String[] tuples = {
            "doc:readme#owner@10", //User 10 is an owner of doc:readme
            "group:eng#member@11", //User 11 is a member of group:eng
            "doc:readme#viewer@group:eng#member", //Members of group:eng are viewers of doc:readme
            "doc:readme#parent@folder:A#..." //doc:readme is in folder:A
        };

        Tuple[] expected = {
            new Tuple(new Tuple.Obj("doc", "readme"), "owner", new Tuple.Usr("10", null)),
            new Tuple(new Tuple.Obj("group", "eng"), "member", new Tuple.Usr("11", null)),
            new Tuple(new Tuple.Obj("doc", "readme"), "viewer", new Tuple.Usr(null, new Tuple.UsrSet(new Tuple.Obj("group", "eng"), "member"))),
            new Tuple(new Tuple.Obj("doc", "readme"), "parent", new Tuple.Usr(null, new Tuple.UsrSet(new Tuple.Obj("folder", "A"), "...")))
        };

        Tuple[] actual = {
            Tuple.parse(tuples[0]),
            Tuple.parse(tuples[1]),
            Tuple.parse(tuples[2]),
            Tuple.parse(tuples[3])
        };

        assertArrayEquals(expected, actual);
    }
}