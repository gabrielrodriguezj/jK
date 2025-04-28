package mx.ipn.escom.k.interpreter;

import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.core.expression.*;

public class Interpreter implements VisitorExpression {

    @Override
    public Object visitAssignmentExpression(AssignmentExpression expression) {

        /*Object res = value.solve(environment);
        environment.assign(name, res);
        */
        return null;
    }

    @Override
    public Object visitArithmeticExpression(ArithmeticExpression expression) {
        /*
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

         */


        return null;
    }

    @Override
    public Object visitCallFunctionExpression(CallFunctionExpression expression) {

/*


@Override
    public Object solve(Environment environment) {
        if(!(callee instanceof VariableExpression)){
            throw new RuntimeException(
                "Expresión no corresponde a llamada a función"
            );
        }
        Token name = ((VariableExpression)callee).name;
        Object function = environment.get(name);

        if(!(function instanceof Function)){
            throw new RuntimeException(
                "No existe la función '" + /*name.getLexema() +* / "'."
        );
        }

List<Object> args = new ArrayList<>();
        for(Expression1 expr : arguments){
Object res = expr.solve(environment);
            args.add(res);
        }

                ((Function)function).call(args);

        return null;
                }

 */




        return null;
    }

    @Override
    public Object visitGetExpression(GetExpression expression) {
        /*

 @Override
    public Object solve(Environment environment) {
        throw new UnsupportedOperationException();
    }



 */


        return null;
    }

    @Override
    public Object visitGroupingExpression(GroupingExpression expression) {

        /*

public Object solve(Environment environment){
        return this.expression.solve(environment);
    }


 */


        return null;
    }

    @Override
    public Object visitLiteralExpression(LiteralExpression expression) {


/*



    public Object solve(Environment environment){
        return this.value;
    }

 */

        return null;
    }

    @Override
    public Object visitLogicalExpression(LogicalExpression expression) {


/*

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
                /*"El operador " + operator.getLexema() +** /
                " sólo se aplica a operandos booleanos"
                        );
                        }


 */



        return null;
    }

    @Override
    public Object visitRelationalExpression(RelationalExpression expression) {

/*


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



 */


        return null;
    }

    @Override
    public Object visitSetExpression(SetExpression expression) {

/*

    @Override
    public Object solve(Environment environment) {
        throw new UnsupportedOperationException();
    }



 */



        return null;
    }

    @Override
    public Object visitSuperExpression(SuperExpression expression) {


/*


    @Override
    public Object solve(Environment environment) {
        throw new UnsupportedOperationException();
    }


 */

        return null;
    }

    @Override
    public Object visitThisExpression(ThisExpression expression) {


/*


    @Override
    public Object solve(Environment environment) {
        throw new UnsupportedOperationException();
    }

 */

        return null;
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression expression) {


/*

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
                /*"El operador " + operator.getLexema() +* /
                        "no se puede aplicar al tipo " +
                                result.getClass().getName()
        );
                }


 */


        return null;
    }

    @Override
    public Object visitVariableExpression(VariableExpression expression) {


/*3


public Object solve(Environment environment){
        return environment.get(name);
    }
}
 */

        return null;
    }
}
