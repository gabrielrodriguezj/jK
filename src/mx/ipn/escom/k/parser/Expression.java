package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;

abstract class Expression {
    public abstract Object solve(Environment environment);
}
