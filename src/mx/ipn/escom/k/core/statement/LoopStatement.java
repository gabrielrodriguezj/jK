package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.expression.Expression;
import mx.ipn.escom.k.core.token.Token;

public record LoopStatement(Token keyword, Expression condition, Statement body) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitLoopStatement(this);
    }
}
