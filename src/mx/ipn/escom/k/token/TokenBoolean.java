package mx.ipn.escom.k.token;

public class TokenBoolean extends TokenConstant<Boolean> {

    public TokenBoolean(TokenName tokenName, Position position) {
        super(tokenName,
                tokenName.equals(TokenName.TRUE),
                position);
    }
}
