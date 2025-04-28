package mx.ipn.escom.k.token;

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
        return "<" + tokenName + ", name: " +  id + ", line: " + line + ">";
    }
}
