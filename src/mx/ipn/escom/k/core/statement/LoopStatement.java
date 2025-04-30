package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.expression.Expression;

public record LoopStatement(Expression condition, Statement body) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitLoopStatement(this);
    }
}
