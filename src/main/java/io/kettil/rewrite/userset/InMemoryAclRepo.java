package io.kettil.rewrite.userset;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kettil.AclRepo;
import io.kettil.rewrite.userset.expression.NamespaceUsersetExpr;
import io.kettil.tuple.Tuple;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryAclRepo implements AclRepo {
    private final static ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    private Map<String, String> namespaces = new HashMap<>();

    @SneakyThrows
    @Override
    public NamespaceUsersetExpr getNamespaceConfig(String name) {
        String json = namespaces.get(name);
        if (json == null)
            return null;

        return MAPPER.readValue(json, NamespaceUsersetExpr.class);
    }

    @SneakyThrows
    @Override
    public void saveNamespaceConfig(NamespaceUsersetExpr namespaceExpr) {
        String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(namespaceExpr);
        namespaces.put(namespaceExpr.getName(), json);
    }

    @Override
    public void deleteNamespaceConfig(String name) {
        namespaces.remove(name);
    }

    @Override
    public Set<String> getUsersByObjectIdAndRelation(String namespace, String objectId, String relation) {
        return null;
    }

    @Override
    public Set<Tuple> getTuplesByObjectIdAndRelation(String namespace, String objectId, String relation) {
        return null;
    }

    @Override
    public void saveTuple(Tuple tuple) {

    }

    @Override
    public void deleteTuple(Tuple tuple) {

    }
}
