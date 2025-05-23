package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.token.Token;

public record GetExpression(Expression object, Token name) implements Expression {

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitGetExpression(this);
    }
}
