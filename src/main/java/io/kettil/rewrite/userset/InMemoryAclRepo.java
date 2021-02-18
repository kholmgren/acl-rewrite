package io.kettil.rewrite.userset;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kettil.rewrite.userset.expression.NamespaceExpr;
import io.kettil.tuple.Tuple;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAclRepo implements AclRepo {
    private final static ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    private Map<String, String> namespaces = new HashMap<>();

    @SneakyThrows
    @Override
    public NamespaceExpr getNamespaceConfig(String name) {
        String json = namespaces.get(name);
        if (json == null)
            return null;

        return MAPPER.readValue(json, NamespaceExpr.class);
    }

    @SneakyThrows
    @Override
    public void saveNamespaceConfig(NamespaceExpr namespaceExpr) {
        String json = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(namespaceExpr);
        namespaces.put(namespaceExpr.getName(), json);
    }

    @Override
    public void deleteNamespaceConfig(String name) {
        namespaces.remove(name);
    }

    @Override
    public List<Tuple> getTuplesByObjectId(String namespace, String objectId) {
        return null;
    }

    @Override
    public List<Tuple> getTuplesByObjectIdAndRelation(String namespace, String objectId, String relation) {
        return null;
    }

    @Override
    public void saveTuple(Tuple tuple) {

    }

    @Override
    public void deleteTuple(Tuple tuple) {

    }
}
