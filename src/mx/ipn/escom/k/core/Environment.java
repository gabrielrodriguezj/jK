package mx.ipn.escom.k.core;

import mx.ipn.escom.k.token.TokenId;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    final Environment enclosing;
    private final Map<String, Object> values = new HashMap<>();

    public Environment() {
        enclosing = null;
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    public Object get(TokenId name) {
        if (values.containsKey(name.getId())) {
            return values.get(name.getId());
        }

        // use of the enclosing to find variables declared
        // in a previous (higher) scope
        if(enclosing != null){
            return enclosing.get(name);
        }

        throw new RuntimeException(
                "Identifier '" + name.getId() +
                        "' not found");
    }

    public void assign(TokenId name, Object value) {
        if (values.containsKey(name.getId())) {
            values.put(name.getId(), value);
            return;
        }

        // use of the enclosing to find variables declared
        // in a previous (higher) scope
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeException(
                "Identifier '" + name.getId() +
                        "' not found");
    }

    public void define(String name, Object value) {
        values.put(name, value);
    }


}
