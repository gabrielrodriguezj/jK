package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;

import java.util.List;

/**
 * @param arguments private final Token paren;
 */
public record CallFunctionExpression(Expression callee, List<Expression> arguments) implements Expression {
    /*Token paren,*/
    // this.paren = paren;

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitCallFunctionExpression(this);
    }
}
