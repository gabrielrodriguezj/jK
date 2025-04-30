package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.token.Token;

public record SetExpression(Expression object, Token name, Expression value) implements Expression {

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitSetExpression(this);
    }
}
