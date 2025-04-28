package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;

public class GroupingExpression implements Expression {
    private final Expression expression;

    public GroupingExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitGroupingExpression(this);
    }

    public Expression getExpression() {
        return expression;
    }
}
