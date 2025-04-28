package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;

public class LiteralExpression implements Expression {
    private final Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitLiteralExpression(this);
    }

    public Object getValue() {
        return value;
    }
}
