//package io.kettil.rewrite.userset;
//
//import io.kettil.AclService;
//import io.kettil.AclRelation;
//import io.kettil.tuple.Tuple;
//import org.junit.Test;
//
//public class ExpandRunnerTest {
//
//    @Test
//    public void expand() {
//        AclService repo = new AclService();
//
//        repo.putRelation(AclRelation.parse("api:acl#enable@acl_admin")); // contact_service's user who calls acl service to create acls for a new contacts
//
//        // acls for service level
//        repo.saveTuple(Tuple.parse("api:contact#enable@group:contactusers#member")); //group
//        repo.saveTuple(Tuple.parse("group:contactusers#member@user1"));
//        repo.saveTuple(Tuple.parse("group:contactusers#member@user2"));
//
//        // acls for object level
//        repo.saveTuple(Tuple.parse("acl:create#owner@acl_admin")); // allow to create acls for acl_admin
//        repo.saveTuple(Tuple.parse("contact:null#owner@group:contactusers#member")); //group permission to create contacts
//
//        repo.saveTuple(Tuple.parse("group:user1#owner@user1")); // user1 is owner of group:user1
//        repo.saveTuple(Tuple.parse("group:user1#editor@user2")); // user2 can edit docs of user1
//        repo.saveTuple(Tuple.parse("group:user2#owner@user2"));  // user2 is owner of group:user2
//        repo.saveTuple(Tuple.parse("group:user2#viewer@user1")); // user1 can only view docs of user2
//        repo.saveTuple(Tuple.parse("contact:null#owner@user2")); // user2 can create new contacts
//
//
//    }
//}