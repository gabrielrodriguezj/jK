package mx.ipn.escom.k.core;

import mx.ipn.escom.k.core.statement.*;

public interface VisitorStatement {
    void visitBlockStatement(BlockStatement statement);
    void visitClassStatement(ClassStatement statement);
    void visitExpressionStatement(ExpressionStatement statement);
    void visitFunctionStatement(FunctionStatement statement);
    void visitIfStatement(IfStatement statement);
    void visitLoopStatement(LoopStatement statement);
    void visitPrintStatement(PrintStatement statement);
    void visitReturnStatement(ReturnStatement statement);
    void visitVarStatement(VarStatement statement);
}
