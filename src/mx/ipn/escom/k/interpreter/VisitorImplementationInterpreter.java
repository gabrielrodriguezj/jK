package mx.ipn.escom.k.interpreter;

import mx.ipn.escom.k.core.*;
import mx.ipn.escom.k.core.exception.RuntimeError;
import mx.ipn.escom.k.core.exception.SemanticException;
import mx.ipn.escom.k.core.expression.*;
import mx.ipn.escom.k.core.statement.*;
import mx.ipn.escom.k.token.TokenName;

import java.util.ArrayList;
import java.util.List;

public class VisitorImplementationInterpreter implements VisitorExpression, VisitorStatement {

    private Environment environment;
    public VisitorImplementationInterpreter(Environment environment) {
        this.environment = environment;
    }

    private Object evaluate(Expression expression){
        return expression.accept(this);
    }

    @Override
    public Object visitAssignmentExpression(AssignmentExpression expression) {

        Object value = evaluate(expression.value());
        environment.assign(expression.name(), value);

        return value;
    }

    @Override
    public Object visitArithmeticExpression(ArithmeticExpression expression) {

        Object left = evaluate(expression.left());
        Object right = evaluate(expression.right());

        // Verify operators
        if(expression.operator().getTokenName() == TokenName.PLUS ||
                expression.operator().getTokenName() == TokenName.MINUS ||
                expression.operator().getTokenName() == TokenName.STAR ||
                expression.operator().getTokenName() == TokenName.SLASH) {

            if (expression.operator().getTokenName() == TokenName.PLUS){
                if(left instanceof Number && right instanceof Number){

                    if(left instanceof Integer && right instanceof Integer){
                        return (Integer)left + (Integer)right;
                    }
                    else{
                        return (Double)left + (Double)right;
                    }
                }
                else if(left instanceof String && right instanceof String){
                    return (String)left + (String)right;
                }
                throw new SemanticException(
                        "Operator '+' cannot be applied to the given operands"
                );
            }
            else{
                if(left instanceof Number && right instanceof Number){

                    if (expression.operator().getTokenName() == TokenName.MINUS){
                        if(left instanceof Integer && right instanceof Integer){
                            return (Integer)left - (Integer)right;
                        }
                        else{
                            return (Double)left - (Double)right;
                        }
                    }

                    if (expression.operator().getTokenName() == TokenName.STAR){
                        if(left instanceof Integer && right instanceof Integer){
                            return (Integer)left * (Integer)right;
                        }
                        else{
                            return (Double)left * (Double)right;
                        }
                    }

                    if (expression.operator().getTokenName() == TokenName.SLASH){
                        if(left instanceof Integer && right instanceof Integer){
                            if((Integer)right != 0){
                                return (Integer)left / (Integer)right;
                            }

                            throw new ArithmeticException("Division by zero");
                        }
                        else{

                            if((Double)right != 0){
                                return (Double)left / (Double)right;
                            }
                            throw new ArithmeticException("Division by zero");
                        }
                    }

                }

                throw new SemanticException(
                        "Incompatible types for operator " + expression.operator()
                );
            }
        }

        throw new SemanticException("Operation not valid");
    }

    /**
     * This method is called when a function is called, not declared.
     * @param expression contains the callee, the arguments and the paren.
     * @return the value returned by the function, if it returns a value, if not, returns null.
     */
    @Override
    public Object visitCallFunctionExpression(CallFunctionExpression expression) {

        // Evaluate the callee element:
        // In the phrase: void main(), main is the callee.
        Object callee = evaluate(expression.callee());

        // Evaluate the arguments:
        List<Object> arguments = new ArrayList<>();
        for(Expression expr : expression.arguments()){
            Object res = evaluate(expr);
            arguments.add(res);
        }

        if (!(callee instanceof KCallable)) {
            throw new RuntimeError(expression.paren(),
                    "Can only call functions and classes.");
        }

        KFunction function = (KFunction) callee;

        // check function's arity
        if (arguments.size() != function.arity()) {
            throw new RuntimeError(expression.paren(), "Expected " +
                    function.arity() + " arguments but got " +
                    arguments.size() + ".");
        }

        return function.call(this, arguments);
    }

    @Override
    public Object visitGetExpression(GetExpression expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object visitGroupingExpression(GroupingExpression expression) {
        return evaluate(expression.expression());
    }

    @Override
    public Object visitLiteralExpression(LiteralExpression expression) {
        return expression.value();
    }

    @Override
    public Object visitLogicalExpression(LogicalExpression expression) {
        Object left = evaluate(expression.left());
        Object right = evaluate(expression.right());

        if(left instanceof Boolean && right instanceof Boolean){
            switch (expression.operator().getTokenName()){
                case AND:
                    return (Boolean)left && (Boolean)right;
                case OR:
                    return (Boolean)left || (Boolean)right;
            }
        }
        throw new SemanticException(
                "Operands not valid for operator '" + expression.operator().getTokenName() + "'");
    }

    @Override
    public Object visitRelationalExpression(RelationalExpression expression) {
        Object left = evaluate(expression.left());
        Object right = evaluate(expression.right());

        // Are != or == operators?
        if(expression.operator().getTokenName() == TokenName.EQUAL_EQUAL ||
                expression.operator().getTokenName() == TokenName.BANG_EQUAL) {

            if((left instanceof Number && right instanceof Number) ||
                    (left instanceof String && right instanceof String) ||
                    (left instanceof Boolean && right instanceof Boolean)){

                if(expression.operator().getTokenName() == TokenName.EQUAL_EQUAL){
                    return left.equals(right);
                }
                return !left.equals(right);
            }
            throw new SemanticException(
                    "Invalid types for operator '" + expression.operator().getTokenName() +
                            "'"
            );
        }

        // Check relational operators
        if(expression.operator().getTokenName() == TokenName.LESS ||
                expression.operator().getTokenName() == TokenName.LESS_EQUAL ||
                expression.operator().getTokenName() == TokenName.GREATER ||
                expression.operator().getTokenName() == TokenName.GREATER_EQUAL) {
            if(left instanceof Number && right instanceof Number){
                switch (expression.operator().getTokenName()){
                    case LESS:
                        return (Double)left < (Double)right;
                    case LESS_EQUAL:
                        return (Double)left <= (Double)right;
                    case GREATER:
                        return (Double)left > (Double)right;
                    case GREATER_EQUAL:
                        return (Double)left >= (Double)right;
                }
            }
            throw new SemanticException(
                    "El operator '" + expression.operator().getTokenName() +
                            "' is valid for non-boolean operands"
            );
        }
        throw new SemanticException("Operation not valid");
    }

    @Override
    public Object visitSetExpression(SetExpression expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object visitSuperExpression(SuperExpression expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object visitThisExpression(ThisExpression expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression expression) {
        Object result = evaluate(expression.right());

        if(expression.operator().getTokenName() == TokenName.MINUS
                && result instanceof Number){
            if(result instanceof Integer)
                return -(Integer)result;
            if(result instanceof Double)
                return -(Double)result;
        }
        else if(expression.operator().getTokenName() == TokenName.BANG &&
                result instanceof Boolean){
            return !(Boolean)result;
        }
        throw new SemanticException(
                "The operator '" + expression.operator().getTokenName() +
                        "cannot be applied to the type: " +
                        result.getClass().getName()
        );
    }

    @Override
    public Object visitVariableExpression(VariableExpression expression) {
        return environment.get(expression.name());
    }

    void execute(Statement statement){
        statement.accept(this);
    }

    @Override
    public void visitBlockStatement(BlockStatement statement) {

        // Keep the highest environment in the stack
        // TODO: Implement a stack of environments instead of a linked list
        Environment local = new Environment(environment);
        Environment previous = environment;

        this.environment = local;

        for(Statement stmt : statement.statements()){
            execute(stmt);
        }

        this.environment = previous;
    }

    @Override
    public void visitClassStatement(ClassStatement statement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        evaluate(statement.expression());
    }

    /**
     * This method is called when a function is declared, not called.
     * @param statement contains the name of the function, parameters and body of the function.
     */
    @Override
    public void visitFunctionStatement(FunctionStatement statement) {
        // Create the callable object for the function.
        KFunction function = new KFunction(statement, environment, false);

        // Define the function in the current environment.
        environment.define(statement.name().getId(), function);
    }

    @Override
    public void visitIfStatement(IfStatement statement) {
        Object condition = evaluate(statement.condition());
        if (!(condition instanceof Boolean)) {
            throw new RuntimeException(
                    "If condition must be a boolean");
        }

        if (((Boolean) condition)) {
            execute(statement.thenBranch());
        } else if (statement.elseBranch() != null) {
            execute(statement.elseBranch());
        }
    }

    @Override
    public void visitLoopStatement(LoopStatement statement) {
        Object condition = evaluate(statement.condition());
        if (!(condition instanceof Boolean)) {
            throw new RuntimeException(
                    "If condition must be a boolean");
        }

        while (((Boolean)condition)){
            execute(statement.body());
            condition = evaluate(statement.condition());
        }
    }

    @Override
    public void visitPrintStatement(PrintStatement statement) {
        Object res = evaluate(statement.expression());
        System.out.println(res);
    }

    @Override
    public void visitReturnStatement(ReturnStatement statement) {
        Object value = null;
        if (statement.value() != null) {
            value = evaluate(statement.value());
        }

        throw new Return(value);
    }

    @Override
    public void visitVarStatement(VarStatement statement) {
        Object init = null;
        if(statement.initializer() != null){
            init = evaluate(statement.initializer());
        }

        environment.define(statement.name().getId(), init);
    }

}
