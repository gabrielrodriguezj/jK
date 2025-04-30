package mx.ipn.escom.k.scanner;

import mx.ipn.escom.k.core.exception.ScannerException;
import mx.ipn.escom.k.core.token.*;

import java.util.HashMap;
import java.util.Map;

public class Scanner {

    private final String source;
    private int current;
    private int line;
    private static final Map<String, TokenName> keyWords;
    private static final Map<String, TokenName> operators;
    private static final Map<String, TokenName> constants;

    static {
        keyWords = new HashMap<>();
        operators = new HashMap<>();
        constants = new HashMap<>();

        keyWords.put("class", TokenName.CLASS);
        keyWords.put("else", TokenName.ELSE);
        keyWords.put("extends", TokenName.EXTENDS);
        keyWords.put("for", TokenName.FOR);
        keyWords.put("fun", TokenName.FUN);
        keyWords.put("if", TokenName.IF);
        keyWords.put("print", TokenName.PRINT);
        keyWords.put("return", TokenName.RETURN);
        keyWords.put("super", TokenName.SUPER);
        keyWords.put("this", TokenName.THIS);
        keyWords.put("var", TokenName.VAR);
        keyWords.put("while", TokenName.WHILE);

        operators.put("and", TokenName.AND);
        operators.put("or", TokenName.OR);

        constants.put("false", TokenName.FALSE);
        constants.put("null", TokenName.NULL);
        constants.put("true", TokenName.TRUE);
    }

    public Scanner(String source){
        this.source = source;
        this.current = 0;
        this.line = 1;
    }

    /*
     * Return the next token
     */
    public Token next() {
        // Every time the parser requires a new token, the scanner
        // must skips "whitespaces"
        skipWhitespace();

        if (isAtEnd()) {
            return new TokenEOF(line);
        }

        char c = advance();

        if (Character.isAlphabetic(c) || c == '_')
            return identifier();
        else if(Character.isDigit(c))
            return number();

        switch (c) {
            case '(': return new TokenPunctuationMarks(TokenName.LEFT_PAREN, line);
            case ')': return new TokenPunctuationMarks(TokenName.RIGHT_PAREN, line);
            case '{': return new TokenPunctuationMarks(TokenName.LEFT_BRACE, line);
            case '}': return new TokenPunctuationMarks(TokenName.RIGHT_BRACE, line);
            case ';': return new TokenPunctuationMarks(TokenName.SEMICOLON, line);
            case ',': return new TokenPunctuationMarks(TokenName.COMMA, line);
            case '.': return new TokenOperator(TokenName.DOT, line);
            case '-': return new TokenOperator(TokenName.MINUS, line);
            case '+': return new TokenOperator(TokenName.PLUS, line);
            case '/': return new TokenOperator(TokenName.SLASH, line);
            case '*': return new TokenOperator(TokenName.STAR, line);
            case '!':
                return new TokenOperator(
                        match('=') ? TokenName.BANG_EQUAL : TokenName.BANG, line);
            case '=':
                return new TokenOperator(
                        match('=') ? TokenName.EQUAL_EQUAL : TokenName.EQUAL, line);
            case '<':
                return new TokenOperator(
                        match('=') ? TokenName.LESS_EQUAL : TokenName.LESS, line);
            case '>':
                return new TokenOperator(
                        match('=') ? TokenName.GREATER_EQUAL : TokenName.GREATER, line);
            case '"': return string();
        }
        
        throw new ScannerException(
                "Character '" + c + "' not valid. Line: " + line);
    }

    /**
     * This function skip whitespaces (tabs, space, line jumps,
     * non-printable characters and comments.
     */
    void skipWhitespace() {
        for (;;) {
            char c = peek();
            switch (c) {
                case ' ':
                case '\r':
                case '\t':
                    advance();
                    break;
                case '\n':
                    line++;
                    advance();
                    break;
                case '/':
                    if (peekNext() == '/') {
                        // Single line comment. This ends with a \n character
                        while (peek() != '\n' && !isAtEnd()) advance();
                    }
                    else if (peekNext() == '*') {
                        // Multiline comment, the "/*" sequence indicate the
                        // beginning of the multiline comment
                        advance(); // consume '/'
                        advance(); // consume '*'

                        // A multiline comment ends with a "* /" sequence
                        do {
                            if (peek() == '*' && peekNext() == '/')
                            {
                                advance(); // consume the '*'
                                advance(); // consume the '/'
                                break;
                            }
                            advance(); // consume any symbol
                            if(peek() == '\n') line++;

                        // The multiline comments still open
                        }while (!isAtEnd());

                    }
                    else {
                        // Do not consume de '/' symbol
                        return;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    private Token identifier() {
        int begin = current - 1;
        while (Character.isAlphabetic(peek()) || Character.isDigit(peek()) || peek() == '_') {
            advance();
        }
        int end = current;

        String lexeme = source.substring(begin, end);

        if(!keyWords.containsKey(lexeme) && !operators.containsKey(lexeme) && !constants.containsKey(lexeme)) {
            return new TokenId(lexeme, line);
        }

        return generateTokenNonIdLexeme(lexeme);
    }

    private Token generateTokenNonIdLexeme(String lexeme) {

        if(keyWords.containsKey(lexeme)){
            return new TokenReservedWord(keyWords.get(lexeme), line);
        }

        if(operators.containsKey(lexeme)){
            return new TokenOperator(operators.get(lexeme), line);
        }

        if(lexeme.equals("true") || lexeme.equals("false")){
            return new TokenBoolean(constants.get(lexeme), line);
        }

        return new TokenNull(line);
    }

    Token number() {
        int begin = current-1;
        while (Character.isDigit(peek())){
            advance();
        }

        // Check if the number contains decimal fraction
        if (peek() == '.' && Character.isDigit(peekNext())) {
            // Consume the dot "."
            advance();

            while (Character.isDigit(peek())){
                advance();
            }
        }
        else if(peek() == '.' && !Character.isDigit(peekNext())){
            throw new ScannerException(
                    "Digit expected after decimal dot. Line: " + line
            );
        }

        if(peek() == 'E' && (peekNext() == '+' || peekNext() == '-' || Character.isDigit(peekNext()))){
            // Consume the symbol "E" for scientific notation
            advance();

            // Consume the sign of the E notation
            if((peek() == '+' || peek() == '-')){
                advance();
            }

            // Reading the numbers of the exponent
            while (Character.isDigit(peek())){
                advance();
            }
        }
        else if(peek() == 'E' && !Character.isDigit(peekNext())){
            throw new ScannerException(
                    "Digit expected after E. Line: " + line
            );
        }

        int end = current;

        String lexeme = source.substring(begin, end);

        // TODO: Detect the right type of data: int, double, float
        double value = Double.parseDouble(lexeme);
        return new TokenNumber(value, line);
    }

    Token string() {
        int begin = current-1;

        while (peek() != '"' && !isAtEnd()) {
            advance();

            if (peek() == '\n') {
                throw new ScannerException(
                        "String was not closed before new line. Line: " + line++
                );
            }

        }
        if (isAtEnd()) {
           throw new ScannerException(
                    "String was not closed properly. Line: " + line
            );
        }

        // Consume the '"' that close the string
        advance();

        int end = current;

        // The lexeme of a string is the string value with the quotes
        // In this case, we remove the quotes from the lexeme for getting
        // the real value of the string
        String strValue = source.substring(begin, end);
        return  new TokenString(strValue, line);
    }


    // Auxiliary functions:

    boolean isAtEnd(){
        return current >= source.length();
    }

    boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;
        current++;
        return true;
    }

    char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    char advance() {
        current++;
        return source.charAt(current-1);
    }

    char  peekNext() {
        if (isAtEnd()) return '\0';
        if(current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }
}

