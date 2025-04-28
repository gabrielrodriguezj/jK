package mx.ipn.escom.k.token;

public class TokenNumber extends TokenConstant<Number> {

    public TokenNumber(Number value, int line) {
        super(TokenName.NUMBER, value, line);
    }

}
