package mx.ipn.escom.k.scanner;

import mx.ipn.escom.k.tools.Position;

/*
Clase sin modificador de acceso para que solo sea accesible desde el paquete
 */
class Buffer {

    private String source;
    private int i;

    private int line;
    private int column;

    private Position lastPosition;
    private Position currentPosition;

    Buffer(String source){
        this.source = source + " ";
        i = -1;
        line = 1;
        column = 0;
        lastPosition = new Position(line, column);
        currentPosition = new Position(line, column);
    }

    boolean hasMoreCharacters(){
        return i < this.source.length() - 1;
    }

    char getNextCharacter(){
        i++;
        char c = this.source.charAt(i);

        // Salto de linea. En ambientes windows el salto de línea se ve así:
        // \r\n
        if (c == '\r' || c == '\n'){
            if(c == '\r'){
                i++;
            }

            line++;
            column = 0;

            return this.source.charAt(i);
        }
        column++;

        lastPosition = currentPosition;
        currentPosition = new Position(line, column);

        return c;
    }

    char getCurrentCharacter(){
        return this.source.charAt(i);
    }

    void backPosition(){
        i--;

        char c = this.source.charAt(i);
        if (c == '\n'){
            i--;
            i--;
        }

        line = lastPosition.getLine();
        column = lastPosition.getColumn();
        currentPosition = lastPosition;
    }

    Position getCurrentPosition(){
        return currentPosition;
    }
}
