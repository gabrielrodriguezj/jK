package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Environment;
import mx.ipn.escom.k.core.Expression;

public class StmtReturn extends Statement {
    final Expression value;

    public StmtReturn(Expression value) {
        this.value = value;
    }

    @Override
    public void execute(Environment environment) {
        throw new UnsupportedOperationException();
    }
}
