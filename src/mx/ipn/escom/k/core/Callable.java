package mx.ipn.escom.k.core;

import java.util.List;

public interface Callable {
    int arity();
    Object call(List<Object> arguments);
}
