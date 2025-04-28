package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;

public record LiteralExpression(Object value) implements Expression {

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitLiteralExpression(this);
    }
}
