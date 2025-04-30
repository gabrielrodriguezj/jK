package mx.ipn.escom.k.core;

import mx.ipn.escom.k.core.statement.*;

public interface VisitorStatement<T> {
    T visitBlockStatement(BlockStatement statement);
    T visitClassStatement(ClassStatement statement);
    T visitExpressionStatement(ExpressionStatement statement);
    T visitFunctionStatement(FunctionStatement statement);
    T visitIfStatement(IfStatement statement);
    T visitLoopStatement(LoopStatement statement);
    T visitPrintStatement(PrintStatement statement);
    T visitReturnStatement(ReturnStatement statement);
    T visitVarStatement(VarStatement statement);
}
