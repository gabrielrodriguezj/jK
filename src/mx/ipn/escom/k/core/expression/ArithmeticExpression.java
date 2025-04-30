package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.token.Token;

public record ArithmeticExpression(Expression left, Token operator, Expression right) implements Expression {

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitArithmeticExpression(this);
    }
}
