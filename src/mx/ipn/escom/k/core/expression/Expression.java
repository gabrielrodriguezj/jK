package mx.ipn.escom.k.core.expression;

public interface Expression {
    <T> T accept(VisitorExpression<T> visitor);
}
