package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.exception.SemanticException;
import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.token.TokenName;
import mx.ipn.escom.k.token.Token;

public class ExprBinary extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprBinary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Object solve(Environment environment){
        Object izq = this.left.solve(environment);
        Object der = this.right.solve(environment);

        // Verificar los operadores aritméticos
        if(operator.getTokenName() == TokenName.PLUS ||
        operator.getTokenName() == TokenName.MINUS ||
        operator.getTokenName() == TokenName.STAR ||
        operator.getTokenName() == TokenName.SLASH) {

            if (operator.getTokenName() == TokenName.PLUS){
                if(izq instanceof Number && der instanceof Number){

                    if(izq instanceof Integer && der instanceof Integer){
                        return (Integer)izq + (Integer)der;
                    }
                    else{
                        return (Double)izq + (Double)der;
                    }
                }
                else if(izq instanceof String && der instanceof String){
                    return (String)izq + (String)der;
                }
                throw new SemanticException(
                        "Incompatibilidad de tipos con el operador +"
                );
            }
            else{
                if(izq instanceof Number && der instanceof Number){

                    if (operator.getTokenName() == TokenName.MINUS){
                        if(izq instanceof Integer && der instanceof Integer){
                            return (Integer)izq - (Integer)der;
                        }
                        else{
                            return (Double)izq - (Double)der;
                        }
                    }

                    if (operator.getTokenName() == TokenName.STAR){
                        if(izq instanceof Integer && der instanceof Integer){
                            return (Integer)izq * (Integer)der;
                        }
                        else{
                            return (Double)izq * (Double)der;
                        }
                    }

                    if (operator.getTokenName() == TokenName.SLASH){
                        if(izq instanceof Integer && der instanceof Integer){
                            if((Integer)der != 0){
                                return (Integer)izq / (Integer)der;
                            }
                            throw new ArithmeticException("Error al dividir por 0");
                        }
                        else{

                            if((Double)der != 0){
                                return (Double)izq / (Double)der;
                            }
                            throw new ArithmeticException("Error al dividir por 0");
                        }
                    }

                }

                throw new SemanticException(
                        "Incompatibilidad de tipos con el operador +"
                );
            }
        }

        throw new SemanticException("Operación no válida");
    }

}
