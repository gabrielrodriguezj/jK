package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;

public class StmtReturn extends Statement {
    final Expression value;

    StmtReturn(Expression value) {
        this.value = value;
    }

    @Override
    public void execute(Environment environment) {
        throw new UnsupportedOperationException();
    }
}
