package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Environment;
import mx.ipn.escom.k.core.Expression;

public class StmtIf extends Statement {
    final Expression condition;
    final Statement thenBranch;
    final Statement elseBranch;

    public StmtIf(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public void execute(Environment environment) {
        /*Object resCondition = condition.solve(environment);
        if(!(resCondition instanceof Boolean)){
            throw new RuntimeException(
                    "La condición no es válida");
        }

        if(((Boolean)resCondition) == true){
            thenBranch.execute(environment);
        }
        else if(elseBranch != null){
            elseBranch.execute(environment);
        }*/
    }
}
