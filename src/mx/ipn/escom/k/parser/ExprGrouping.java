package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;

public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression;
    }

    public Object solve(Environment environment){
        return this.expression.solve(environment);
    }
}
