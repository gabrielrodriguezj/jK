package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;

public class ReturnStatement implements Statement {
    private final Expression value;

    public ReturnStatement(Expression value) {
        this.value = value;
    }

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitReturnStatement(this);
    }

    public Expression getValue(){
        return value;
    }
}
