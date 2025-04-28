package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.token.Token;

public class ExprSet extends Expression{
    final Expression object;
    final Token name;
    final Expression value;

    ExprSet(Expression object, Token name, Expression value) {
        this.object = object;
        this.name = name;
        this.value = value;
    }

    @Override
    public Object solve(Environment environment) {
        throw new UnsupportedOperationException();
    }
}
