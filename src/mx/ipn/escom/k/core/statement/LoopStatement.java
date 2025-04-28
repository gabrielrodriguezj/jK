package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;

public class LoopStatement implements Statement {
    private final Expression condition;
    private final Statement body;

    public LoopStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitLoopStatement(this);
    }

    public Expression getCondition(){
        return condition;
    }

    public Statement getBody(){
        return body;
    }
}
