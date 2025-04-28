package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;

import java.util.List;

public class CallFunctionExpression implements Expression {
    private final Expression callee;
    // private final Token paren;
    private final List<Expression> arguments;

    public CallFunctionExpression(Expression callee, /*Token paren,*/ List<Expression> arguments) {
        this.callee = callee;
        // this.paren = paren;
        this.arguments = arguments;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitCallFunctionExpression(this);
    }

    public Expression getCallee() {
        return callee;
    }

    public List<Expression> getArguments() {
        return arguments;
    }
}
