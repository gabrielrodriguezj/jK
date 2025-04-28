package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

/**
 * @param method private final Token keyword;
 */
public record SuperExpression(Token method) implements Expression {
    // this.keyword = keyword;

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitSuperExpression(this);
    }
}
