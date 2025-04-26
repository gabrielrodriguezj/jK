package mx.ipn.escom.k.scanner;

import mx.ipn.escom.k.tools.Position;
import mx.ipn.escom.k.tools.TipoToken;
import mx.ipn.escom.k.tools.Token;

public class RelationalScanner {
    private final Buffer buffer;

    RelationalScanner (Buffer buffer){
        this.buffer = buffer;
    }

    Token getToken(){
        char caracter = buffer.getCurrentCharacter();
        int estado = 0;
        Position position = buffer.getCurrentPosition();

        while (true){
            switch (estado){
                case 0:
                    if(caracter == '<'){
                        estado = 1;
                    }
                    else if(caracter =='>'){
                        estado = 2;
                    }
                    else if(caracter =='!'){
                        estado = 3;
                    }
                    else if(caracter =='='){
                        estado = 4;
                    }

                    break;

                case 1:
                    if(caracter == '='){
                        return new Token(TipoToken.LESS_EQUAL, "<=", position);
                    }
                    else{
                        buffer.backPosition();
                        return new Token(TipoToken.LESS, "<", position);
                    }
                case 2:
                    if(caracter == '='){
                        return new Token(TipoToken.GREATER_EQUAL, ">=", position);
                    }
                    else{
                        buffer.backPosition();
                        return new Token(TipoToken.GREATER, ">", position);
                    }
                case 3:
                    if(caracter == '='){
                        return new Token(TipoToken.BANG_EQUAL, "!=", position);
                    }
                    else{
                        buffer.backPosition();
                        return new Token(TipoToken.BANG, "!", position);
                    }
                case 4:
                    if(caracter == '='){
                        return new Token(TipoToken.EQUAL_EQUAL, "==", position);
                    }
                    else{
                        buffer.backPosition();
                        return new Token(TipoToken.EQUAL, "=", position);
                    }

            }
            caracter = buffer.getNextCharacter();
        }
    }
}
