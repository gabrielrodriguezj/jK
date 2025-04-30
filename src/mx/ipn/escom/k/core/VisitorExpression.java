package mx.ipn.escom.k.core;

import mx.ipn.escom.k.core.expression.*;

public interface VisitorExpression<T> {
    T visitAssignmentExpression(AssignmentExpression expression);
    T visitArithmeticExpression(ArithmeticExpression expression);
    T visitCallFunctionExpression(CallFunctionExpression expression);
    T visitGetExpression(GetExpression expression);
    T visitGroupingExpression(GroupingExpression expression);
    T visitLiteralExpression(LiteralExpression expression);
    T visitLogicalExpression(LogicalExpression expression);
    T visitRelationalExpression(RelationalExpression expression);
    T visitSetExpression(SetExpression expression);
    T visitSuperExpression(SuperExpression expression);
    T visitThisExpression(ThisExpression expression);
    T visitUnaryExpression(UnaryExpression expression);
    T visitVariableExpression(VariableExpression expression);
}
