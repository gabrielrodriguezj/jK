package mx.ipn.escom.k.core.token;

public enum TokenName {
    // Tokens with just one character
    LEFT_PAREN("("),
    RIGHT_PAREN(")"),
    LEFT_BRACE("{"),
    RIGHT_BRACE("}"),
    COMMA(","),
    DOT("."),
    MINUS("-"),
    PLUS("+"),
    SEMICOLON(";"),
    SLASH("/"),
    STAR("*"),

    // Tokens with one or two characters
    BANG("!"),
    BANG_EQUAL("!="),
    EQUAL("="),
    EQUAL_EQUAL("=="),
    GREATER(">"),
    GREATER_EQUAL(">="),
    LESS("<"),
    LESS_EQUAL("<="),

    // Literals
    IDENTIFIER("id"),
    STRING("''"),
    NUMBER("0.0"),

    // Keyword
    AND("and"),
    CLASS("class"),
    ELSE("else"),
    FALSE("false"),
    FUN("fun"),
    FOR("for"),
    IF("if"),
    NULL("null"),
    OR("or"),
    PRINT("print"),
    RETURN("return"),
    SUPER("super"),
    THIS("this"),
    TRUE("true"),
    VAR("var"),
    WHILE("while"),
    EXTENDS("extends"),

    EOF("$");

    private final String lexeme;

    TokenName(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return lexeme;
    }
}
