package mx.ipn.escom.k.token;

public abstract class Token {

    final TokenName tokenName;
    final int line;

    public Token(TokenName tokenName, int line) {
        this.tokenName = tokenName;
        this.line = line;
    }

    public String toString(){
        return "<" + tokenName + ", " + line + ">";
    }

    public TokenName getTokenName() {
        return tokenName;
    }

    public int getLine() {
        return line;
    }
}
