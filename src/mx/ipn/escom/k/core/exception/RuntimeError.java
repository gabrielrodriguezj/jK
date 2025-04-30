package mx.ipn.escom.k.core.exception;

import mx.ipn.escom.k.core.token.Token;

public class RuntimeError extends KError {
    private final Token token;

    public RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
