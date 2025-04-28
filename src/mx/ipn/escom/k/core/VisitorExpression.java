package mx.ipn.escom.k.core;

import mx.ipn.escom.k.core.expression.*;

public interface VisitorExpression {
    Object visitAssignmentExpression(AssignmentExpression expression);
    Object visitArithmeticExpression(ArithmeticExpression expression);
    Object visitCallFunctionExpression(CallFunctionExpression expression);
    Object visitGetExpression(GetExpression expression);
    Object visitGroupingExpression(GroupingExpression expression);
    Object visitLiteralExpression(LiteralExpression expression);
    Object visitLogicalExpression(LogicalExpression expression);
    Object visitRelationalExpression(RelationalExpression expression);
    Object visitSetExpression(SetExpression expression);
    Object visitSuperExpression(SuperExpression expression);
    Object visitThisExpression(ThisExpression expression);
    Object visitUnaryExpression(UnaryExpression expression);
    Object visitVariableExpression(VariableExpression expression);
}
