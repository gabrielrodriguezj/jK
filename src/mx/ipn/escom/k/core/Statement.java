package mx.ipn.escom.k.core;

public interface Statement {
    void accept(VisitorStatement visitor);
}
