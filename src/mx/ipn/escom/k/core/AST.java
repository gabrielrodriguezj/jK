package mx.ipn.escom.k.core;

import mx.ipn.escom.k.core.statement.Statement;

import java.util.List;

public class AST {
    private final List<Statement> statements;

    public AST(List<Statement> statements){
        this.statements = statements;
    }

    public void toInterpret(Environment environment){
        for(Statement stmt : statements){
            stmt.execute(environment);
        }
    }

}
