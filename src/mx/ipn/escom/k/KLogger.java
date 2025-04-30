package mx.ipn.escom.k;

import mx.ipn.escom.k.core.exception.RuntimeError;
import mx.ipn.escom.k.core.token.Token;
import mx.ipn.escom.k.core.token.TokenName;

public class KLogger {

    private static KLogger instance;
    private boolean hasError = false;
    private boolean hasRuntimeError = false;

    private KLogger() {
    }

    public static KLogger getInstance(){
        if(instance == null){
            instance = new KLogger();
        }
        return instance;
    }

    public void error(int line, String message) {
        report(line, "", message);
    }

    public void error(Token token, String message) {
        if (token.getTokenName() == TokenName.EOF) {
            report(token.getLine(), " at end", message);
        } else {
            report(token.getLine(), " at '" + token + "'", message);
        }
    }

    public void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() +
                "\n[line " + error.getToken().getLine() + "]");
        hasRuntimeError = true;
    }

    private void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hasError = true;
    }

    public boolean hasError() {
        return hasError;
    }

    public boolean hasRuntimeError() {
        return hasRuntimeError;
    }
}

