package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;
import mx.ipn.escom.k.token.Token;
import mx.ipn.escom.k.token.TokenId;

import java.util.List;

public record FunctionStatement(TokenId name, List<Token> params, BlockStatement body) implements Statement {

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitFunctionStatement(this);
    }
}
