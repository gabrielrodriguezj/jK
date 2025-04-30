package mx.ipn.escom.k.core.expression;

public class ThisExpression implements Expression {
    // private final Token keyword;

    public ThisExpression() {
        // this.keyword = keyword;
    }

    @Override
    public <T> T accept(VisitorExpression<T> visitor) {
        return visitor.visitThisExpression(this);
    }
}
