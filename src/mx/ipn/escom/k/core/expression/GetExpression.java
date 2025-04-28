package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

public class GetExpression implements Expression {
    private final Expression object;
    private final Token name;

    public GetExpression(Expression object, Token name) {
        this.object = object;
        this.name = name;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitGetExpression(this);
    }

    public Expression getObject() {
        return object;
    }

    public Token getName() {
        return name;
    }
}
