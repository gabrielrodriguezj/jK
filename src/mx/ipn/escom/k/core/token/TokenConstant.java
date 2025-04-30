package mx.ipn.escom.k.core.token;

public abstract class TokenConstant <T> extends Token {

    public T value;

    public TokenConstant(TokenName tokenName, T value, int line) {
        super(tokenName, line);
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
