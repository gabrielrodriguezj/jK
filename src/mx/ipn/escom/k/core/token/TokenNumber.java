package mx.ipn.escom.k.core.token;

public class TokenNumber extends TokenConstant<Number> {

    public TokenNumber(Number value, int line) {
        super(TokenName.NUMBER, value, line);
    }

    @Override
    public String toString(){
        return value.toString();
    }

}
