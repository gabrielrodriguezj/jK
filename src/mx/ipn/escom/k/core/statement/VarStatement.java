package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;
import mx.ipn.escom.k.token.TokenId;

public record VarStatement(TokenId name, Expression initializer) implements Statement {

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitVarStatement(this);
    }
}
