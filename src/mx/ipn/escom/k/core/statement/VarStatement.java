package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.expression.Expression;
import mx.ipn.escom.k.core.token.TokenId;

public record VarStatement(TokenId name, Expression initializer) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitVarStatement(this);
    }
}
