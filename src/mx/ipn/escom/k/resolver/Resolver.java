package mx.ipn.escom.k.resolver;

import mx.ipn.escom.k.core.AST;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.interpreter.Interpreter;
import mx.ipn.escom.k.interpreter.VisitorImplementationInterpreter;

public class Resolver {

    private final VisitorImplementationResolver resolver;

    public Resolver(Interpreter interpreter) {
        VisitorImplementationInterpreter interpreterVisitor = Interpreter.getInstance().getInterpreterVisitor();
        this.resolver = new VisitorImplementationResolver(interpreterVisitor);
    }

    public void analyze(AST ast) {
        for(Statement statement : ast.statements()) {
            statement.accept(resolver);
        }
    }

}
