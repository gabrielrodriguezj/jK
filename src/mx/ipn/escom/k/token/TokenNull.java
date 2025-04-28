package mx.ipn.escom.k.token;

public class TokenNull extends TokenConstant<Object> {
    public TokenNull(TokenName tokenName, Position position) {
        super(tokenName, null, position);
    }
}
