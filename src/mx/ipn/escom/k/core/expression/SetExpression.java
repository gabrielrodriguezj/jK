package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.token.Token;

public class SetExpression implements Expression {
    private final Expression object;
    private final Token name;
    private final Expression value;

    public SetExpression(Expression object, Token name, Expression value) {
        this.object = object;
        this.name = name;
        this.value = value;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitSetExpression(this);
    }

    public Expression getObject() {
        return object;
    }

    public Token getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }
}
