package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;

public class ExpressionStatement implements Statement {
    private final Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitExpressionStatement(this);
    }

    public Expression getExpression(){
        return expression;
    }
}
