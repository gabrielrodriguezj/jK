package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;
import mx.ipn.escom.k.core.expression.VariableExpression;
import mx.ipn.escom.k.token.Token;

import java.util.List;

public class ClassStatement implements Statement {
    private final Token name;
    private final VariableExpression superclass;
    private final List<Statement> attributesAndMethods;

    public ClassStatement(Token name, VariableExpression superclass, List<Statement> attributesAndMethods) {
        this.name = name;
        this.superclass = superclass;
        this.attributesAndMethods = attributesAndMethods;
    }

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitClassStatement(this);
    }

    public Token getName(){
        return name;
    }

    public VariableExpression getSuperclass(){
        return superclass;
    }

    public List<Statement> getAttributesAndMethods(){
        return attributesAndMethods;
    }
}
