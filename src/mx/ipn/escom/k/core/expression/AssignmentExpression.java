package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

public record AssignmentExpression(Token name, Expression value) implements Expression {

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitAssignmentExpression(this);
    }
}
