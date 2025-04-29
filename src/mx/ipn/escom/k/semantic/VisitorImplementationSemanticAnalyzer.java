package mx.ipn.escom.k.semantic;

import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.core.VisitorStatement;
import mx.ipn.escom.k.core.expression.*;
import mx.ipn.escom.k.core.statement.*;

public class VisitorImplementationSemanticAnalyzer implements VisitorExpression, VisitorStatement {
    @Override
    public Object visitAssignmentExpression(AssignmentExpression expression) {
        return null;
    }

    @Override
    public Object visitArithmeticExpression(ArithmeticExpression expression) {
        return null;
    }

    @Override
    public Object visitCallFunctionExpression(CallFunctionExpression expression) {
        return null;
    }

    @Override
    public Object visitGetExpression(GetExpression expression) {
        return null;
    }

    @Override
    public Object visitGroupingExpression(GroupingExpression expression) {
        return null;
    }

    @Override
    public Object visitLiteralExpression(LiteralExpression expression) {
        return null;
    }

    @Override
    public Object visitLogicalExpression(LogicalExpression expression) {
        return null;
    }

    @Override
    public Object visitRelationalExpression(RelationalExpression expression) {
        return null;
    }

    @Override
    public Object visitSetExpression(SetExpression expression) {
        return null;
    }

    @Override
    public Object visitSuperExpression(SuperExpression expression) {
        return null;
    }

    @Override
    public Object visitThisExpression(ThisExpression expression) {
        return null;
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression expression) {
        return null;
    }

    @Override
    public Object visitVariableExpression(VariableExpression expression) {
        return null;
    }

    @Override
    public void visitBlockStatement(BlockStatement statement) {

    }

    @Override
    public void visitClassStatement(ClassStatement statement) {

    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {

    }

    @Override
    public void visitFunctionStatement(FunctionStatement statement) {

    }

    @Override
    public void visitIfStatement(IfStatement statement) {

    }

    @Override
    public void visitLoopStatement(LoopStatement statement) {

    }

    @Override
    public void visitPrintStatement(PrintStatement statement) {

    }

    @Override
    public void visitReturnStatement(ReturnStatement statement) {

    }

    @Override
    public void visitVarStatement(VarStatement statement) {

    }
}
