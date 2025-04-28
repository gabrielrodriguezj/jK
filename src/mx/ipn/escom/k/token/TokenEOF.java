package mx.ipn.escom.k.token;

public class TokenEOF extends Token {
    public TokenEOF(int line) {
        super(TokenName.EOF, line);
    }

    @Override
    public String toString() {
        return "<$>";
    }
}
