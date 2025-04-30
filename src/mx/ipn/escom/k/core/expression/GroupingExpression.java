package mx.ipn.escom.k.core.expression;

public record GroupingExpression(Expression expression) implements Expression {

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitGroupingExpression(this);
    }
}
