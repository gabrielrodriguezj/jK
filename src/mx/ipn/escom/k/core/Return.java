package mx.ipn.escom.k.core;

public class Return extends RuntimeException {
    final Object value;

    public Return(Object value) {
        // Super arguments:
        // - message: null
        // - cause: null
        // - enableSuppression: false
        // - writableStackTrace: false
        super(null, null, false, false);
        this.value = value;
    }
}
