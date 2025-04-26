package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;

import java.util.List;

public class StmtBlock extends Statement{
    final List<Statement> statements;

    StmtBlock(List<Statement> statements) {
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
