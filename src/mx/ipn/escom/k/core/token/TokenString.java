package mx.ipn.escom.k.core.token;

public class TokenString extends TokenConstant<String> {

    public TokenString(String value, int line) {
        super(TokenName.STRING, value, line);
    }

    @Override
    public String toString(){
        return "\"" + value + "\"";
    }
}
