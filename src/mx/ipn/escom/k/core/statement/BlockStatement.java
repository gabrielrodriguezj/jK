package mx.ipn.escom.k.core.statement;

import java.util.List;

public record BlockStatement(List<Statement> statements) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitBlockStatement(this);
    }
}
