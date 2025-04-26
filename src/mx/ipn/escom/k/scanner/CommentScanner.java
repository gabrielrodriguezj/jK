package mx.ipn.escom.k.scanner;

import mx.ipn.escom.k.tools.Position;
import mx.ipn.escom.k.tools.TipoToken;
import mx.ipn.escom.k.tools.Token;

public class CommentScanner {

    private final Buffer buffer;

    public CommentScanner(Buffer buffer) {
        this.buffer = buffer;
    }

    Token getToken(){
        char caracter = ' ';
        Position position = buffer.getCurrentPosition();
        int estado = 1;

        while(buffer.hasMoreCharacters()){
            caracter = buffer.getNextCharacter();

            switch (estado){
                case 1:
                    if(caracter == '/'){
                        estado = 2;
                    }
                    else if(caracter == '*'){
                        estado = 3;
                    }
                    else{
                        buffer.backPosition();

                        return new Token(TipoToken.SLASH, "/", position);
                    }
                    break;
                case 2:
                    if(caracter == '\n'){
                        // Se retorna null porque el comentario no genera token
                        return null;
                    }
                case 3:
                    if(caracter == '*'){
                        estado = 4;
                    }
                    break;
                case 4:
                    if(caracter == '/'){
                        // Se retorna null porque el comentario no genera token
                        return null;
                    }
                    else{
                        estado = 3;
                    }
                    break;
            }
        }
        return null;
    }
}
