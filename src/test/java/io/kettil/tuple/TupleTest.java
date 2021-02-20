package io.kettil.tuple;

import io.kettil.AclRelation;
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

        AclRelation[] expected = {
            new AclRelation("doc", "readme", "owner", "10"),
            new AclRelation("group", "eng", "member", "11"),
            new AclRelation("doc", "readme", "viewer", "group", "eng", "member"),
            new AclRelation("doc", "readme", "parent", "folder", "A", "..."),
        };


        for (int i = 0; i < tuples.length; i++) {
            AclRelation relation = AclRelation.parse(tuples[i]);
            assertEquals(expected[i], relation);
        }
    }

}