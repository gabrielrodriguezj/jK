package mx.ipn.escom.k.core.exception;

public class KError extends RuntimeException {
    public KError() {
        super();
    }
    public KError(String message) {
        super(message);
    }
}
