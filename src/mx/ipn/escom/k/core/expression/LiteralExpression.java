package mx.ipn.escom.k.core.expression;

public record LiteralExpression(Object value) implements Expression {

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitLiteralExpression(this);
    }
}
