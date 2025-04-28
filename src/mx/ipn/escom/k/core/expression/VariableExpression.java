package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;
import mx.ipn.escom.k.token.TokenId;

public record VariableExpression(TokenId name) implements Expression {

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitVariableExpression(this);
    }
}
