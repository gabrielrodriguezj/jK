package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;

public class StmtExpression extends Statement {
    final Expression expression;

    StmtExpression(Expression expression) {
        this.expression = expression;
    }

    public void execute(Environment environment){
        expression.solve(environment);
    }
}
