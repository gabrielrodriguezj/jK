package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

public class VariableExpression implements Expression {
    private final Token name;

    public VariableExpression(Token name) {
        this.name = name;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitVariableExpression(this);
    }

    public Token getName() {
        return name;
    }
}
