package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;
import mx.ipn.escom.k.token.Token;
import mx.ipn.escom.k.token.TokenId;

import java.util.List;

public class FunctionStatement implements Statement {
    private final TokenId name;
    private final List<Token> params;
    private final BlockStatement body;

    public FunctionStatement(TokenId name, List<Token> params, BlockStatement body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitFunctionStatement(this);
    }

    public TokenId getName() {
        return name;
    }

    public List<Token> getParams(){
        return params;
    }

    public BlockStatement getBody(){
        return body;
    }
}
