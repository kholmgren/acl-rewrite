package io.kettil.tuple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TupleTest {
    @Test
    public void parse() {
        String[] tuples = {
            "doc:readme#owner@10", //User 10 is an owner of doc:readme
            "group:eng#member@11", //User 11 is a member of group:eng
            "doc:readme#viewer@group:eng#member", //Members of group:eng are viewers of doc:readme
            "doc:readme#parent@folder:A#...", //doc:readme is in folder:A
        };

//        Tuple[] expected = {
//            new Tuple(new TupleObject("doc", "readme"), "owner", new TupleUser("10", null)),
//            new Tuple(new TupleObject("group", "eng"), "member", new TupleUser("11", null)),
//            new Tuple(new TupleObject("doc", "readme"), "viewer", new TupleUser(null, new TupleUserset(new TupleObject("group", "eng"), "member"))),
//            new Tuple(new TupleObject("doc", "readme"), "parent", new TupleUser(null, new TupleUserset(new TupleObject("folder", "A"), "..."))),
//        };

        Tuple[] expected = {
            new Tuple("doc", "readme", "owner", "10"),
            new Tuple("group", "eng", "member", "11"),
            new Tuple("doc", "readme", "viewer", "group", "eng", "member"),
            new Tuple("doc", "readme", "parent", "folder", "A", "..."),
        };


        for (int i = 0; i < tuples.length; i++) {
            Tuple tuple = Tuple.parse(tuples[i]);
            assertEquals(expected[i], tuple);
        }
    }

//    @Test
//    public void parseAny() {
//        String[] tuples = {
//            "doc:readme",
//            "doc:readme#viewer",
//            "doc:readme#viewer@group:eng"
//        };
//
//        Tuple[] expected = {
//            new Tuple(new TupleObject("doc", "readme"), "*", new TupleUser("*", null)),
//            new Tuple(new TupleObject("doc", "readme"), "viewer", new TupleUser("*", null)),
//            new Tuple(new TupleObject("doc", "readme"), "viewer", new TupleUser(null, new TupleUserset(new TupleObject("group", "eng"), "*")))
//        };
//
//        for (int i = 0; i < tuples.length; i++) {
//            System.out.print(tuples[i]);
//            Tuple tuple = Tuple.parse(tuples[i]);
//            System.out.println("=" + tuple);
//            assertEquals(expected[i], tuple);
//        }
//    }
}