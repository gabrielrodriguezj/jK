package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.TokenPunctuationMarks;

import java.util.List;

/**
 * @param arguments private final Token paren;
 */
public record CallFunctionExpression(Expression callee, TokenPunctuationMarks paren, List<Expression> arguments) implements Expression {

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitCallFunctionExpression(this);
    }
}
