package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.token.TokenPunctuationMarks;

import java.util.List;

/**
 * @param arguments private final Token paren;
 */
public record CallExpression(Expression callee, TokenPunctuationMarks paren, List<Expression> arguments) implements Expression {

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitCallExpression(this);
    }
}
