package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

public record RelationalExpression(Expression left, Token operator, Expression right) implements Expression {

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitRelationalExpression(this);
    }

}
