package mx.ipn.escom.k.token;

public class TokenNull extends TokenConstant<Object> {
    public TokenNull(int line) {
        super(TokenName.NULL, null, line);
    }
}
