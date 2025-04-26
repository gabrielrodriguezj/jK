package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;

public abstract class Statement {
    public abstract void execute(Environment environment);
}