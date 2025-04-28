package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.exception.SemanticException;
import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.token.TokenName;
import mx.ipn.escom.k.token.Token;

public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

    public Object solve(Environment environment){
        Object result = this.right.solve(environment);

        if(this.operator.getTokenName() == TokenName.MINUS
        && result instanceof Number){
            if(result instanceof Integer)
                return -(Integer)result;
            if(result instanceof Double)
                return -(Double)result;
        }
        else if(this.operator.getTokenName() == TokenName.BANG &&
        result instanceof Boolean){
            return !(Boolean)result;
        }
        throw new SemanticException(
                /*"El operador " + operator.getLexema() +*/
                        "no se puede aplicar al tipo " +
                        result.getClass().getName()
        );
    }
}
