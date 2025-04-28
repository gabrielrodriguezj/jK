package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

public class SuperExpression implements Expression {
    // private final Token keyword;
    private final Token method;

    public SuperExpression(Token method) {
        // this.keyword = keyword;
        this.method = method;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitSuperExpression(this);
    }

    public Token getMethod() {
        return method;
    }
}
