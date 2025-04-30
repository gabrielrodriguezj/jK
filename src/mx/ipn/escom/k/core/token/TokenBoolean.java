package mx.ipn.escom.k.core.token;

public class TokenBoolean extends TokenConstant<Boolean> {

    public TokenBoolean(TokenName tokenName, int line) {
        super(tokenName,
                tokenName.equals(TokenName.TRUE),
                line);
    }

    @Override
    public String toString(){
        return "<BOOLEAN, value: " +  value + ", line: " + line + ">";
    }
}
