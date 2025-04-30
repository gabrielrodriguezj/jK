package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.expression.Expression;
import mx.ipn.escom.k.core.token.Token;

public record IfStatement(Token keyword, Expression condition, Statement thenBranch, Statement elseBranch) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitIfStatement(this);
    }
}
