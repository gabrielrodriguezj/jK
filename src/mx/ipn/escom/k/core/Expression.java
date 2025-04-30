package mx.ipn.escom.k.core;

public interface Expression {
    <T> T accept(VisitorExpression<T> visitor);
}
