package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.interpreter.Environment;
import mx.ipn.escom.k.interpreter.Function;
import mx.ipn.escom.k.token.Token;

import java.util.ArrayList;
import java.util.List;

public class ExprCallFunction extends Expression{
    final Expression callee;
    // final Token paren;
    final List<Expression> arguments;

    ExprCallFunction(Expression callee, /*Token paren,*/ List<Expression> arguments) {
        this.callee = callee;
        // this.paren = paren;
        this.arguments = arguments;
    }

    @Override
    public Object solve(Environment environment) {
        if(!(callee instanceof ExprVariable)){
            throw new RuntimeException(
                "Expresión no corresponde a llamada a función"
            );
        }
        Token name = ((ExprVariable)callee).name;
        Object function = environment.get(name);

        if(!(function instanceof Function)){
            throw new RuntimeException(
                "No existe la función '" + /*name.getLexema() +*/ "'."
            );
        }

        List<Object> args = new ArrayList<>();
        for(Expression expr : arguments){
            Object res = expr.solve(environment);
            args.add(res);
        }

        ((Function)function).call(args);

        return null;
    }
}
