package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.exception.SemanticException;
import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.tools.TipoToken;
import mx.ipn.escom.k.tools.Token;

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
        if(operator.getTipo() == TipoToken.EQUAL_EQUAL ||
                operator.getTipo() == TipoToken.BANG_EQUAL) {

            if((izq instanceof Number && der instanceof Number) ||
                    (izq instanceof String && der instanceof String) ||
                    (izq instanceof Boolean && der instanceof Boolean)){

                if(operator.getTipo() == TipoToken.EQUAL_EQUAL){
                    return izq.equals(der);
                }
                return !izq.equals(der);
            }
            throw new SemanticException(
                    "El operador " + operator.getLexema() +
                            "No se puede aplicar a distintos tipos"
            );
        }

        // Verificar los operadores relacionales
        if(operator.getTipo() == TipoToken.LESS ||
                operator.getTipo() == TipoToken.LESS_EQUAL ||
                operator.getTipo() == TipoToken.GREATER ||
                operator.getTipo() == TipoToken.GREATER_EQUAL) {
            if(izq instanceof Number && der instanceof Number){
                switch (operator.getTipo()){
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
                    "El operador " + operator.getLexema() +
                            " no se puede aplicar a valores" +
                            " no booleanos"
            );
        }
        throw new SemanticException("Operación no válida");
    }
}
