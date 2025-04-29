package mx.ipn.escom.k.core;

import mx.ipn.escom.k.core.statement.FunctionStatement;
import mx.ipn.escom.k.interpreter.VisitorImplementationInterpreter;
import mx.ipn.escom.k.token.TokenId;

import java.util.List;

/**
 * This class represents a function, its callable element.
 */
public class KFunction implements KCallable {

    // The declaration of the function. Contains the name, parameters and body.
    private final FunctionStatement declaration;

    // The environment in which the function was declared.
    private final Environment closure;


    private final boolean isInitializer;

    public KFunction(FunctionStatement declaration, Environment closure,
                     boolean isInitializer) {
        this.declaration = declaration;
        this.closure = closure;
        this.isInitializer = isInitializer;
    }

    KFunction bind() {
        Environment environment = new Environment(closure);
        return new KFunction(declaration, environment, isInitializer);
    }

    @Override
    public int arity() {
        return declaration.params().size();
    }

    /**
     * Call the function. Execute the body of the function.
     * @param interpreter is the interpreter that executes the body of the function.
     * @param arguments are the arguments passed to the function.
     * @return the value returned by the function, in case it returns a value, if not, returns null.
     */
    @Override
    public Object call(VisitorImplementationInterpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.params().size(); i++) {
            environment.define(
                    ((TokenId)declaration.params().get(i)).getId(),
                arguments.get(i)
            );
        }

        // The function could return a value
        try {
            interpreter.visitBlockStatement(declaration.body());
        }
        catch (Return returnValue) {
            return returnValue.value;
        }

        return null;
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name().getId() + ">";
    }
}
