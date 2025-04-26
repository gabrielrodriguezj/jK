package mx.ipn.escom.k.tools;

public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final Position position;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
        this.position = null;
    }

    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.position = null;
    }

    public Token(TipoToken tipo, String lexema, Position position) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
        this.position = position;
    }

    public Token(TipoToken tipo, String lexema, Object literal, Position position) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.position = position;
    }

    public String toString(){
        return "<" + tipo +
                (lexema.equals("") ? "" : ", " + lexema) +
                (literal == null ? "" : ", " + literal) +
                (position == null ? "" : ", " + position.getLine() + ":" + position.getColumn()) +
                ">";
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public Object getLiteral() {
        return literal;
    }

    public Position getPosition() {
        return position;
    }
}
