package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Environment;

import java.util.List;

public class StmtBlock extends Statement{
    final List<Statement> statements;

    public StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void execute(Environment environment) {
        Environment local = new Environment(environment);

        for(Statement stmt : statements){
            stmt.execute(local);
        }
    }
}
