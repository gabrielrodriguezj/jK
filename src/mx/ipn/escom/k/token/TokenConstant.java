package mx.ipn.escom.k.token;

public abstract class TokenConstant <T> extends Token {

    public T value;

    public TokenConstant(TokenName tokenName, T value, Position position) {
        super(tokenName, position);
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
