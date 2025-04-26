package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;

public class StmtPrint extends Statement {
    final Expression expression;

    StmtPrint(Expression expression) {
        this.expression = expression;
    }

    public void execute(Environment environment){
        Object res = expression.solve(environment);
        System.out.println(res);
    }
}
