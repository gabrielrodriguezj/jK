package mx.ipn.escom.k.interpreter;

import mx.ipn.escom.k.core.*;

/**
 * Singleton pattern because we only need one interpreter and one global environment.
 */
public class Interpreter  {

    private static Interpreter instance;
    private final VisitorImplementationInterpreter interpreter;

    private Interpreter() {
        Environment environment = new Environment();
        this.interpreter = new VisitorImplementationInterpreter(environment);
    }

    public static Interpreter getInstance(){
        if(instance == null){
            instance = new Interpreter();
        }
        return instance;
    }

    public VisitorImplementationInterpreter getInterpreterVisitor(){
        return this.interpreter;
    }

    public void interpret(AST ast) {
        for(Statement statement : ast.statements()){
            statement.accept(this.interpreter);
        }
    }
}
