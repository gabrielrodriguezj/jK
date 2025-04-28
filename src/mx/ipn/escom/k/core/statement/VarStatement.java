package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorStatement;
import mx.ipn.escom.k.token.Token;

public class VarStatement implements Statement {
    private final Token name;
    private final Expression initializer;

    public VarStatement(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public void accept(VisitorStatement visitor) {
        visitor.visitVarStatement(this);
    }

    public Token getName(){
        return name;
    }

    public Expression getInitializer(){
        return initializer;
    }
}
