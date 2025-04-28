package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

public class AssignmentExpression implements Expression {
    private final Token name;
    private final Expression value;

    public AssignmentExpression(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitAssignmentExpression(this);
    }

    public Token getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }
}
