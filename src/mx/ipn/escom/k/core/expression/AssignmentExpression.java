package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.token.TokenId;

public record AssignmentExpression(TokenId name, Expression value) implements Expression {

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitAssignmentExpression(this);
    }
}
