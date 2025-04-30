package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.expression.VariableExpression;
import mx.ipn.escom.k.core.token.Token;

import java.util.List;

public record ClassStatement(Token name, VariableExpression superclass,
                             List<Statement> attributesAndMethods) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitClassStatement(this);
    }
}
