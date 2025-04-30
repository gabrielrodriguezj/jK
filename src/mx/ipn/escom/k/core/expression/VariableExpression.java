package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.token.TokenId;

public record VariableExpression(TokenId name) implements Expression {

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitVariableExpression(this);
    }
}
