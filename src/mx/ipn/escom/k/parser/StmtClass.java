package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.token.Token;

import java.util.List;

public class StmtClass extends Statement {
    final Token name;
    final ExprVariable superclass;
    final List<Statement> attributesAndMethods;

    StmtClass(Token name, ExprVariable superclass, List<Statement> attributesAndMethods) {
        this.name = name;
        this.superclass = superclass;
        this.attributesAndMethods = attributesAndMethods;
    }

    @Override
    public void execute(Environment environment) {
        throw new UnsupportedOperationException();
    }
}
