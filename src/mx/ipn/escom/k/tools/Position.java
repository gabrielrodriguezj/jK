package mx.ipn.escom.k.tools;

public class Position {
    final int line;
    final int column;

    public Position(int line, int column){
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return line + ":" + column;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }
}
