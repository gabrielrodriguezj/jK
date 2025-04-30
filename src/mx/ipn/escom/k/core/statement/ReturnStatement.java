package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.expression.Expression;

public record ReturnStatement(Expression value) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitReturnStatement(this);
    }
}
