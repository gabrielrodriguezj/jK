package mx.ipn.escom.k.core.exception;

import mx.ipn.escom.k.token.Token;

public class RuntimeError extends RuntimeException {
    final Token token;

    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }
}
