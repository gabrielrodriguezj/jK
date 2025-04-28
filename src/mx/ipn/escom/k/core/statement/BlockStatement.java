package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;

import java.util.List;

public record BlockStatement(List<Statement> statements) implements Statement {

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitBlockStatement(this);
    }
}
