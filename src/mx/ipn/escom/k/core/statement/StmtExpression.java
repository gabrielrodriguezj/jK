package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Environment;
import mx.ipn.escom.k.core.Expression;

public class StmtExpression extends Statement {
    final Expression expression;

    public StmtExpression(Expression expression) {
        this.expression = expression;
    }

    public void execute(Environment environment){
        // expression.solve(environment);
    }
}
