package mx.ipn.escom.k.core.statement;

import mx.ipn.escom.k.core.token.TokenId;

import java.util.List;

public record FunctionStatement(TokenId name, List<TokenId> params, BlockStatement body) implements Statement {

    @Override
    public <T> T accept(VisitorStatement<T> visitor) {
        return visitor.visitFunctionStatement(this);
    }
}
