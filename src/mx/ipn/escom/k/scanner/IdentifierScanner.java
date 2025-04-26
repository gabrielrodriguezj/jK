package mx.ipn.escom.k.scanner;

import mx.ipn.escom.k.tools.Position;
import mx.ipn.escom.k.tools.TipoToken;
import mx.ipn.escom.k.tools.Token;

import java.util.HashMap;
import java.util.Map;

class IdentifierScanner {

    private static final Map<String, TipoToken> palabrasReservadas;
    private final Buffer buffer;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("class", TipoToken.CLASS);
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("extends", TipoToken.EXTENDS);
        palabrasReservadas.put("false", TipoToken.FALSE);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("fun", TipoToken.FUN); //definir funciones
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("null", TipoToken.NULL);
        palabrasReservadas.put("or", TipoToken.OR);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("this", TipoToken.THIS);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("var", TipoToken.VAR);
        palabrasReservadas.put("while", TipoToken.WHILE);
    }

    IdentifierScanner(Buffer buffer){
        this.buffer = buffer;
    }

    Token getToken(){
        String lexema = String.valueOf(buffer.getCurrentCharacter());
        Position position = buffer.getCurrentPosition();
        char caracter = ' ';

        while(true){
            caracter = buffer.getNextCharacter();

            if(Character.isAlphabetic(caracter) || Character.isDigit(caracter)){
                lexema += caracter;
            }
            else{
                buffer.backPosition();

                TipoToken tt = palabrasReservadas.get(lexema);
                if(tt == null){
                    return new Token(TipoToken.IDENTIFIER, lexema, position);
                }
                else{
                    if(tt == TipoToken.TRUE){
                        return new Token(tt, lexema, true, position);
                    }
                    else if(tt == TipoToken.FALSE){
                        return new Token(tt, lexema, false, position);
                    }
                    else if(tt == TipoToken.NULL){
                        return new Token(tt, lexema, null, position);
                    }
                    return new Token(tt, lexema, position);
                }
            }
        }

    }
}
