package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.exception.SemanticException;
import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.token.TokenName;
import mx.ipn.escom.k.token.Token;

public class ExprRelational extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprRelational(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Object solve(Environment environment){
        Object izq = this.left.solve(environment);
        Object der = this.right.solve(environment);

        // Verificar los operadores relacionales de comparación
        if(operator.getTokenName() == TokenName.EQUAL_EQUAL ||
                operator.getTokenName() == TokenName.BANG_EQUAL) {

            if((izq instanceof Number && der instanceof Number) ||
                    (izq instanceof String && der instanceof String) ||
                    (izq instanceof Boolean && der instanceof Boolean)){

                if(operator.getTokenName() == TokenName.EQUAL_EQUAL){
                    return izq.equals(der);
                }
                return !izq.equals(der);
            }
            throw new SemanticException(
                    "El operador " + operator.getTokenName() +
                            "No se puede aplicar a distintos tipos"
            );
        }

        // Verificar los operadores relacionales
        if(operator.getTokenName() == TokenName.LESS ||
                operator.getTokenName() == TokenName.LESS_EQUAL ||
                operator.getTokenName() == TokenName.GREATER ||
                operator.getTokenName() == TokenName.GREATER_EQUAL) {
            if(izq instanceof Number && der instanceof Number){
                switch (operator.getTokenName()){
                    case LESS:
                        return (Double)izq < (Double)der;
                    case LESS_EQUAL:
                        return (Double)izq <= (Double)der;
                    case GREATER:
                        return (Double)izq > (Double)der;
                    case GREATER_EQUAL:
                        return (Double)izq >= (Double)der;
                }
            }
            throw new SemanticException(
                    "El operador " + operator.getTokenName() +
                            " no se puede aplicar a valores" +
                            " no booleanos"
            );
        }
        throw new SemanticException("Operación no válida");
    }
}
