package mx.ipn.escom.k.core.statement;

public interface Statement {
    <T> T accept(VisitorStatement<T> visitor);
}
