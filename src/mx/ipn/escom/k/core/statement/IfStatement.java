package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.expression.Expression;

public record IfStatement(Expression condition, Statement thenBranch, Statement elseBranch) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitIfStatement(this);
    }
}
