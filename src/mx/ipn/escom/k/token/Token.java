package mx.ipn.escom.k.token;

public abstract class Token {

    final TokenName tokenName;
    final Position position;

    public Token(TokenName tokenName, Position position) {
        this.tokenName = tokenName;
        this.position = position;
    }

    public String toString(){
        return "<" + tokenName +
                (position == null ? "" : ", " + position.getLine() + ":" + position.getColumn()) +
                ">";
    }

    public TokenName getTokenName() {
        return tokenName;
    }

    public Position getPosition() {
        return position;
    }
}
