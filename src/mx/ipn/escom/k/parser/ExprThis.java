package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.tools.Token;

public class ExprThis extends Expression{
    // final Token keyword;

    ExprThis() {
        // this.keyword = keyword;
    }

    @Override
    public Object solve(Environment environment) {
        throw new UnsupportedOperationException();
    }
}