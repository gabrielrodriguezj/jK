package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Environment;
import mx.ipn.escom.k.core.Expression;

public class StmtPrint extends Statement {
    final Expression expression;

    public StmtPrint(Expression expression) {
        this.expression = expression;
    }

    public void execute(Environment environment){
        /*Object res = expression.solve(environment);
        System.out.println(res);*/
    }
}
