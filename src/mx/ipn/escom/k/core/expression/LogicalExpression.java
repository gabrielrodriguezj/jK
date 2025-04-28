package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

public class LogicalExpression implements Expression {
    private final Expression left;
    private final Token operator;
    private final Expression right;

    public LogicalExpression(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitLogicalExpression(this);
    }

    public Expression getLeft() {
        return left;
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }

}
