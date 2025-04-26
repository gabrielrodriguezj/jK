package mx.ipn.escom.k.scanner;

import mx.ipn.escom.k.exception.ScannerException;
import mx.ipn.escom.k.tools.TipoToken;
import mx.ipn.escom.k.tools.Token;

import java.util.ArrayList;
import java.util.List;

public class Scanner {

    private final Buffer buffer;

    private final List<Token> tokens = new ArrayList<>();

    public Scanner(String source){
        this.buffer = new Buffer(source);
    }

    public List<Token> scan() throws Exception {

        while(buffer.hasMoreCharacters()){
            char caracter = buffer.getNextCharacter();

            if(caracter == '{'){
                tokens.add(new Token(TipoToken.LEFT_BRACE, "{", null, buffer.getCurrentPosition()));
            }
            else if(caracter == '}'){
                tokens.add(new Token(TipoToken.RIGHT_BRACE, "}", null, buffer.getCurrentPosition()));
            }
            else if(caracter == '('){
                tokens.add(new Token(TipoToken.LEFT_PAREN, "(", null, buffer.getCurrentPosition()));
            }
            else if(caracter == ')'){
                tokens.add(new Token(TipoToken.RIGHT_PAREN, ")", null, buffer.getCurrentPosition()));
            }
            else if(caracter == '+'){
                tokens.add(new Token(TipoToken.PLUS, "+", null, buffer.getCurrentPosition()));
            }
            else if(caracter == '-'){
                tokens.add(new Token(TipoToken.MINUS, "-", null, buffer.getCurrentPosition()));
            }
            else if(caracter == '*'){
                tokens.add(new Token(TipoToken.STAR, "*", null, buffer.getCurrentPosition()));
            }
            else if(caracter == ','){
                tokens.add(new Token(TipoToken.COMMA, ",", null, buffer.getCurrentPosition()));
            }
            else if(caracter == '.'){
                tokens.add(new Token(TipoToken.DOT, ".", null, buffer.getCurrentPosition()));
            }
            else if(caracter == ';'){
                tokens.add(new Token(TipoToken.SEMICOLON, ";", null, buffer.getCurrentPosition()));
            }
            else if(caracter == '/'){
                Token t = (new CommentScanner(buffer)).getToken();

                // Si no es un comentario
                if(t != null){
                    tokens.add(t);
                }

            }
            else if(caracter == '='){
                Token t = (new RelationalScanner(buffer)).getToken();
                tokens.add(t);
            }
            else if(caracter == '<'){
                Token t = (new RelationalScanner(buffer)).getToken();
                tokens.add(t);
            }
            else if(caracter == '>'){
                Token t = (new RelationalScanner(buffer)).getToken();
                tokens.add(t);
            }
            else if(caracter == '!'){
                Token t = (new RelationalScanner(buffer)).getToken();
                tokens.add(t);
            }
            else if(caracter == '"'){
                Token t = (new StringScanner(buffer)).getToken();
                tokens.add(t);
            }

            else if(Character.isAlphabetic(caracter)){
                Token t = (new IdentifierScanner(buffer)).getToken();
                tokens.add(t);
            }
            else if(Character.isDigit(caracter)){
                Token t = (new NumberScanner(buffer)).getToken();
                tokens.add(t);
            }
            else if(caracter == ' ' || caracter== '\t' ||
                    caracter == '\n' || caracter == '\r'){
                continue;
            }
            else{
                String message = "Caracter '" + caracter +
                        "' no válido en la posición " +
                        buffer.getCurrentPosition();
                throw new ScannerException(message);
            }
        }

        tokens.add(new Token(TipoToken.EOF, "", buffer.getCurrentPosition()));
        //tokens.add(new Token(TipoToken.EOF, "");

        return tokens;
    }
}

