package mx.ipn.escom.k.interpreter;

import mx.ipn.escom.k.parser.Statement;

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
