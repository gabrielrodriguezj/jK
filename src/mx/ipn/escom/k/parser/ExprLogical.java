package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.exception.SemanticException;
import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.token.Token;

public class ExprLogical extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprLogical(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Object solve(Environment environment){
        Object izq = left.solve(environment);
        Object der = right.solve(environment);

        if(izq instanceof Boolean && der instanceof Boolean){
            switch (operator.getTokenName()){
                case AND:
                    return (Boolean)izq && (Boolean)der;
                case OR:
                    return (Boolean)izq || (Boolean)der;
            }
        }
        throw new SemanticException(
                /*"El operador " + operator.getLexema() +**/
                " sólo se aplica a operandos booleanos"
        );
    }
}

