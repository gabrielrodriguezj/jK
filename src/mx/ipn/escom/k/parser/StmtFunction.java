package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.interpreter.Function;
import mx.ipn.escom.k.token.Token;
import mx.ipn.escom.k.token.TokenId;

import java.util.List;

public class StmtFunction extends Statement {
    final TokenId name;
    final List<Token> params;
    final StmtBlock body;

    StmtFunction(TokenId name, List<Token> params, StmtBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public List<Token> getParams(){
        return params;
    }

    public StmtBlock getBody(){
        return body;
    }
    @Override
    public void execute(Environment environment) {
        Function function = new Function(this, environment, false);
        environment.define(name.getId(), function);
    }
}
