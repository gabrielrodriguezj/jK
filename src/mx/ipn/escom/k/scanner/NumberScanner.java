package mx.ipn.escom.k.scanner;

import mx.ipn.escom.k.exception.ScannerException;
import mx.ipn.escom.k.tools.Position;
import mx.ipn.escom.k.tools.TipoToken;
import mx.ipn.escom.k.tools.Token;

public class NumberScanner {
    private final Buffer buffer;

    NumberScanner(Buffer buffer){
        this.buffer = buffer;
    }

    Token getToken() throws ScannerException {
        String lexema = String.valueOf(buffer.getCurrentCharacter());
        Position position = buffer.getCurrentPosition();
        /*
        Para garantizar que un número tiene al menos un dígito, este AFD comienza
        con el estado 1, ya que, de algún modo, se podría considerar que el estado 0
        está en la clase scanner.
         */
        int estado = 1;
        char caracter = ' ';

        while (true){
            caracter = buffer.getNextCharacter();

            switch (estado){
                case 1:
                    if(Character.isDigit(caracter)){
                        lexema = lexema + caracter;
                    }
                    else if(caracter == '.'){
                        estado = 2;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == 'E'){
                        estado = 4;
                        lexema = lexema + caracter;
                    }
                    else{
                        buffer.backPosition();
                        return new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema), position);
                    }
                    break;
                case 2:
                    if(Character.isDigit(caracter)){
                        estado = 3;
                        lexema = lexema + caracter;
                    }
                    else{
                        String message = "Número no válido en la posición " +
                                position;
                        throw new ScannerException(message);
                    }
                    break;
                case 3:
                    if(Character.isDigit(caracter)){
                        // estado = 2;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == 'E'){
                        estado = 4;
                        lexema = lexema + caracter;
                    }
                    else{
                        buffer.backPosition();
                        return new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema), position);
                    }
                    break;
                case 4:
                    if(caracter == '+' || caracter == '-'){
                        estado = 5;
                        lexema = lexema + caracter;
                    }
                    else if(Character.isDigit(caracter)){
                        estado = 6;
                        lexema = lexema + caracter;
                    }
                    else{
                        String message = "Número no válido en la posición " +
                                position;
                        throw new ScannerException(message);
                    }
                    break;
                case 5:
                    if(Character.isDigit(caracter)){
                        estado = 6;
                        lexema = lexema + caracter;
                    }
                    else{
                        String message = "Número no válido en la posición " +
                                position;
                        throw new ScannerException(message);
                    }
                    break;
                case 6:
                    if(Character.isDigit(caracter)){
                        lexema = lexema + caracter;
                    }
                    else{
                        buffer.backPosition();
                        return new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema), position);
                    }
                    break;
            }

        }
    }
}
