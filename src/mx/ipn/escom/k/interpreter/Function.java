package mx.ipn.escom.k.interpreter;

import mx.ipn.escom.k.parser.StmtFunction;

import java.util.List;

public class Function implements  Callable{

    private final StmtFunction declaration;
    private final Environment closure;
    private final boolean isInitializer;

    public Function(StmtFunction declaration, Environment closure,
                boolean isInitializer) {
        this.isInitializer = isInitializer;
        this.closure = closure;
        this.declaration = declaration;
    }

    Function bind() {
        Environment environment = new Environment(closure);
        return new Function(declaration, environment, isInitializer);
    }

    @Override
    public int arity() {
        return declaration.getParams().size();
    }

    @Override
    public Object call(List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < declaration.getParams().size(); i++) {
            environment.define(
                declaration.getParams().get(i).getLexema(),
                arguments.get(i)
            );
        }

        declaration.getBody().execute(environment);
        try{
         //declaration.execute(environment);
        }
        catch (Exception ex){
            if (isInitializer){
                // return closure.getAt(0, "this");
            }
            // return returnValue.value;
        }

        if (isInitializer){
            // return closure.getAt(0, "this");
        }
        return null;
    }
}
