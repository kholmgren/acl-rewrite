//package io.kettil.rewrite.expand;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.kettil.rewrite.expand.expression.ExpandExpr;
//import io.kettil.rewrite.userset.InMemoryAclRepo;
//import io.kettil.rewrite.userset.NamespaceConfigExprFactory;
//import io.kettil.rewrite.userset.expression.NamespaceConfigExpr;
//import io.kettil.tuple.Tuple;
//import org.junit.Test;
//
//public class ExpandExpressionBuilderTest {
//    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();
//
//    @Test
//    public void eval() throws Exception {
//        InMemoryAclRepo repo = new InMemoryAclRepo();
//
//        NamespaceConfigExpr namespaceConfig = NamespaceConfigExprFactory.parse(getClass().getClassLoader().getResourceAsStream("rewrite_rule_build_test.txt"));
//
//        repo.saveNamespaceConfig(namespaceConfig);
//
////        repo.saveTuple(Tuple.parse("api:acl#enable@acl_admin")); // contact_service's user who calls acl service to create acls for a new contacts
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
//        ExpandExpr eval = ExpandExpressionBuilder.eval(repo, "contact", "null", "owner");
//
//
//        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(eval));
//    }
//}