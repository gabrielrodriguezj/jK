package mx.ipn.escom.k.core;

import mx.ipn.escom.k.interpreter.VisitorImplementationInterpreter;

import java.util.List;

public interface KCallable {
    int arity();
    Object call(VisitorImplementationInterpreter interpreter, List<Object> arguments);
}
