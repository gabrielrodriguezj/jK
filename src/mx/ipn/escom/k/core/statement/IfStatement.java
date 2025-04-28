package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;

public class IfStatement implements Statement {
    private final Expression condition;
    private final Statement thenBranch;
    private final Statement elseBranch;

    public IfStatement(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitIfStatement(this);
    }

    public Expression getCondition(){
        return condition;
    }

    public Statement getThenBranch(){
        return thenBranch;
    }

    public Statement getElseBranch(){
        return elseBranch;
    }
}
