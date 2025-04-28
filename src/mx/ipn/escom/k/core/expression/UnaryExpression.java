package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

public class UnaryExpression implements Expression {
    private final Token operator;
    private final Expression right;

    public UnaryExpression(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitUnaryExpression(this);
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }
}
