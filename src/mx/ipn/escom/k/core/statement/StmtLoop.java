package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Environment;
import mx.ipn.escom.k.core.Expression;

public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    public StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(Environment environment) {
        /*Object resCondition = condition.solve(environment);
        if(!(resCondition instanceof Boolean)){
            throw new RuntimeException(
                    "La condición no es válida");
        }

        while (((Boolean)resCondition) == true){
            body.execute(environment);
            resCondition = condition.solve(environment);
        }*/
    }
}
