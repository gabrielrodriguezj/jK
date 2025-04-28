package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.TokenId;

public record AssignmentExpression(TokenId name, Expression value) implements Expression {

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitAssignmentExpression(this);
    }
}
