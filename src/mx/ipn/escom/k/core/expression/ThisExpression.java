package mx.ipn.escom.k.core.expression;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.VisitorExpression;

public class ThisExpression implements Expression {
    // private final Token keyword;

    public ThisExpression() {
        // this.keyword = keyword;
    }

    @Override
    public Object accept(VisitorExpression visitor) {
        return visitor.visitThisExpression(this);
    }
}
