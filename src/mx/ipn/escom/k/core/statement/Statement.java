package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Environment;

public abstract class Statement {
    public abstract void execute(Environment environment);
}