package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.expression.Expression;

public record ExpressionStatement(Expression expression) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitExpressionStatement(this);
    }
}
