package mx.ipn.escom.k.scanner;

import mx.ipn.escom.k.exception.ScannerException;
import mx.ipn.escom.k.tools.Position;
import mx.ipn.escom.k.tools.TipoToken;
import mx.ipn.escom.k.tools.Token;

public class StringScanner {

    private final Buffer buffer;

    StringScanner(Buffer buffer){
        this.buffer = buffer;
    }

    Token getToken() throws ScannerException {
        String lexema = String.valueOf(buffer.getCurrentCharacter());
        Position position = buffer.getCurrentPosition();
        int estado = 0;
        char caracter = ' ';

        while (true) {
            caracter = buffer.getNextCharacter();

            if(caracter == '"'){
                lexema += caracter;
                String literal = lexema.substring(1, lexema.length() - 1);
                return new Token(TipoToken.STRING, lexema, literal, position);
            }
            else if(caracter == '\n'){
                String message = "Fin de cadena no no encontrado en la linea " +
                        position.getLine();
                throw new ScannerException(message);
            }
            else{
                lexema += caracter;
            }
        }
    }
}
