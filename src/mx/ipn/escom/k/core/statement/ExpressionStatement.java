package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;

public record ExpressionStatement(Expression expression) implements Statement {

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitExpressionStatement(this);
    }
}
