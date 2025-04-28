package mx.ipn.escom.k.core;

public interface Expression {
    Object accept(VisitorExpression visitor);
}
