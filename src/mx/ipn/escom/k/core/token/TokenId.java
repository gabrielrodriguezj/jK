package mx.ipn.escom.k.core.token;

public class TokenId extends Token {

    private final String id;

    public TokenId(String id, int line) {
        super(TokenName.IDENTIFIER, line);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString(){
        return id;
    }
}
