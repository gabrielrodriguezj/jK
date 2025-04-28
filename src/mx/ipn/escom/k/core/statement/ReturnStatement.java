package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;

public record ReturnStatement(Expression value) implements Statement {

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitReturnStatement(this);
    }
}
