package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.token.Token;

public class StmtVar extends Statement {
    final Token name;
    final Expression initializer;

    StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    public void execute(Environment environment){
        Object init = null;
        if(initializer != null){
            init = initializer.solve(environment);
        }

        /*environment.define(name.getLexema(), init);*/
    }
}
