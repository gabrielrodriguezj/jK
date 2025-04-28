package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Environment;
import mx.ipn.escom.k.core.expression.VariableExpression;
import mx.ipn.escom.k.token.Token;

import java.util.List;

public class StmtClass extends Statement {
    final Token name;
    final VariableExpression superclass;
    final List<Statement> attributesAndMethods;

    public StmtClass(Token name, VariableExpression superclass, List<Statement> attributesAndMethods) {
        this.name = name;
        this.superclass = superclass;
        this.attributesAndMethods = attributesAndMethods;
    }

    @Override
    public void execute(Environment environment) {
        throw new UnsupportedOperationException();
    }
}
