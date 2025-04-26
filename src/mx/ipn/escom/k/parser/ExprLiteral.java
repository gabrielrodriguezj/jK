package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;

class ExprLiteral extends Expression {
    final Object value;

    ExprLiteral(Object value) {
        this.value = value;
    }

    public Object solve(Environment environment){
        return this.value;
    }
}
