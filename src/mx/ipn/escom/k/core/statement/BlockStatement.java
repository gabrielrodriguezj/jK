package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;

import java.util.List;

public class BlockStatement implements Statement {
    private final List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitBlockStatement(this);
    }

    public List<Statement> getStatements(){
        return statements;
    }
}
