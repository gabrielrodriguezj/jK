package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;
import mx.ipn.escom.k.core.expression.VariableExpression;
import mx.ipn.escom.k.token.Token;

import java.util.List;

public record ClassStatement(Token name, VariableExpression superclass,
                             List<Statement> attributesAndMethods) implements Statement {

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitClassStatement(this);
    }
}
