package mx.ipn.escom.k.core;

public interface Statement {
    <T> T accept(VisitorStatement<T> visitor);
}
