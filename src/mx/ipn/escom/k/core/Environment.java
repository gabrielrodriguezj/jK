package mx.ipn.escom.k.core;

import mx.ipn.escom.k.token.Token;
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
        //if (values.containsKey(name.getLexema())) {
        //    return values.get(name.getLexema());
        //}

        // uso del enclosing para usar variables declaradas
        // en un "scope" previo
        if(enclosing != null){
            return enclosing.get(name);
        }

        /*throw new RuntimeException(
                "Variable '" + name.getLexema() +
                        "'. no definida");*/

        return null;
    }

    public void assign(Token name, Object value) {
        /*if (values.containsKey(name.getLexema())) {
            values.put(name.getLexema(), value);
            return;
        }

        // uso del enclosing para usar variables declaradas
        // en un "scope" previo
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeException(
                "Variable '" + name.getLexema() +
                        "'. no definida");*/
        return;
    }

    public void define(String name, Object value) {
        values.put(name, value);
    }


}
