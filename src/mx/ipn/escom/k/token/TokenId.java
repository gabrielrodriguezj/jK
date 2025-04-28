package mx.ipn.escom.k.token;

public class TokenId extends Token {

    private final String id;

    public TokenId(TokenName tokenName, String id, Position position) {
        super(tokenName, position);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
