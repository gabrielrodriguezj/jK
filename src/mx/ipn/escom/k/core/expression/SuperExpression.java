package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.token.Token;

/**
 * @param method private final Token keyword;
 */
public record SuperExpression(Token method) implements Expression {
    // this.keyword = keyword;

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitSuperExpression(this);
    }
}
