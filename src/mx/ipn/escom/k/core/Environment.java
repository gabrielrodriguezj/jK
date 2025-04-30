package mx.ipn.escom.k.core;

import mx.ipn.escom.k.core.exception.RuntimeError;
import mx.ipn.escom.k.core.token.TokenId;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    // Used to implement the concept of Lexical scopes
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

        throw new RuntimeError(name,
                "Undefined variable '" + name.getId() + "'.");
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

        throw new RuntimeError(name,
                "Undefined variable '" + name.getId() + "'.");
    }

    public void define(String name, Object value) {
        values.put(name, value);
    }

    Environment ancestor(int distance) {
        Environment environment = this;
        for (int i = 0; i < distance; i++) {
            // Theoretically, this should never happen
            if (environment == null) {break;}
            environment = environment.enclosing;
        }

        return environment;
    }

    public Object getAt(int distance, String name) {
        return ancestor(distance).values.get(name);
    }

    public void assignAt(int distance, TokenId name, Object value) {
        ancestor(distance).values.put(name.getId(), value);
    }

    @Override
    public String toString() {
        String result = values.toString();
        if (enclosing != null) {
            result += " -> " + enclosing.toString();
        }

        return result;
    }


}
