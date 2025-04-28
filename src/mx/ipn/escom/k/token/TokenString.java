package mx.ipn.escom.k.token;

public class TokenString extends TokenConstant<String> {

    public TokenString(String value, int line) {
        super(TokenName.STRING, value, line);
    }
}
