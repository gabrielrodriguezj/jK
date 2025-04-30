package mx.ipn.escom.k.resolver;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.VisitorExpression;
import mx.ipn.escom.k.core.VisitorStatement;
import mx.ipn.escom.k.core.exception.SemanticException;
import mx.ipn.escom.k.core.expression.*;
import mx.ipn.escom.k.core.statement.*;
import mx.ipn.escom.k.interpreter.VisitorImplementationInterpreter;
import mx.ipn.escom.k.token.TokenId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * This is a variable resolution pass. It works like a sort of mini-interpreter.
 * It walks the tree, visiting each node, but a static analysis is different from
 * a dynamic execution:
 * -->There are no side effects:
 *      When the static analysis visits a print statement,
 *      it doesn't print anything. Calls to native functions
 *      or other operations that reach out to the outside
 *      world are stubbed out and have no effect.
 * -->There is no control flow:
 *      Loops are visited only once. Both branches are
 *      visited in if statements. Logic operators are not
 *      short-circuited.
 */
public class VisitorImplementationResolver implements VisitorExpression<Void>, VisitorStatement<Void> {

    private enum FunctionType {
        NONE,
        FUNCTION,
        INITIALIZER,
        METHOD
    }

    private final VisitorImplementationInterpreter interpreter;
    private final Stack<Map<String, Boolean>> scopes = new Stack<>();
    private FunctionType currentFunction = FunctionType.NONE;

    public VisitorImplementationResolver(VisitorImplementationInterpreter interpreter){
        this.interpreter = interpreter;
    }

    @Override
    public Void visitAssignmentExpression(AssignmentExpression expression) {
        resolve(expression.value());
        resolveLocal(expression.value(), expression.name());
        return null;
    }

    @Override
    public Void visitArithmeticExpression(ArithmeticExpression expression) {
        resolve(expression.left());
        resolve(expression.right());
        return null;
    }

    @Override
    public Void visitCallFunctionExpression(CallFunctionExpression expression) {
        resolve(expression.callee());

        for (Expression argument : expression.arguments()) {
            resolve(argument);
        }

        return null;
    }

    @Override
    public Void visitGetExpression(GetExpression expression) {
        return null;
    }

    @Override
    public Void visitGroupingExpression(GroupingExpression expression) {
        resolve(expression.expression());
        return null;
    }

    @Override
    public Void visitLiteralExpression(LiteralExpression expression) {
        return null;
    }

    @Override
    public Void visitLogicalExpression(LogicalExpression expression) {
        resolve(expression.left());
        resolve(expression.right());
        return null;
    }

    @Override
    public Void visitRelationalExpression(RelationalExpression expression) {
        resolve(expression.left());
        resolve(expression.right());
        return null;
    }

    @Override
    public Void visitSetExpression(SetExpression expression) {
        return null;
    }

    @Override
    public Void visitSuperExpression(SuperExpression expression) {
        return null;
    }

    @Override
    public Void visitThisExpression(ThisExpression expression) {
        return null;
    }

    @Override
    public Void visitUnaryExpression(UnaryExpression expression) {
        resolve(expression.right());
        return null;
    }

    @Override
    public Void visitVariableExpression(VariableExpression expression) {
        if (!scopes.isEmpty() &&
                scopes.peek().get(expression.name().getId()) == Boolean.FALSE) {

            throw new SecurityException(
                    "Can't read local '" + expression.name() + "' variable in its own initializer."
            );
        }

        resolveLocal(expression, expression.name());
        return null;
    }

    /**
     * This begins a new scope, traverses into the statements inside the block, and then discards the scope.
     * @param statement
     */
    @Override
    public Void visitBlockStatement(BlockStatement statement) {
        beginScope();
        resolve(statement.statements());
        endScope();

        return null;
    }

    @Override
    public Void visitClassStatement(ClassStatement statement) {
        return null;
    }

    @Override
    public Void visitExpressionStatement(ExpressionStatement statement) {
        resolve(statement.expression());

        return null;
    }

    @Override
    public Void visitFunctionStatement(FunctionStatement statement) {
        declare(statement.name());
        define(statement.name());

        resolveFunction(statement, FunctionType.FUNCTION);

        return null;
    }

    @Override
    public Void visitIfStatement(IfStatement statement) {
        resolve(statement.condition());
        resolve(statement.thenBranch());
        if (statement.elseBranch() != null){
            resolve(statement.elseBranch());
        }

        return null;
    }

    @Override
    public Void visitLoopStatement(LoopStatement statement) {
        resolve(statement.condition());
        resolve(statement.body());

        return null;
    }

    @Override
    public Void visitPrintStatement(PrintStatement statement) {
        resolve(statement.expression());

        return null;
    }

    @Override
    public Void visitReturnStatement(ReturnStatement statement) {
        if (currentFunction == FunctionType.NONE) {
            throw new SemanticException(
                    "Can't return from top-level code.");
        }

        if (statement.value() != null) {
            resolve(statement.value());
        }

        return null;
    }

    @Override
    public Void visitVarStatement(VarStatement statement) {
        declare(statement.name());
        if (statement.initializer() != null) {
            resolve(statement.initializer());
        }
        define(statement.name());

        return null;
    }

    private Void resolve(List<Statement> statements) {
        for (Statement statement : statements) {
            resolve(statement);
        }

        return null;
    }

    // Using the visitor pattern. These methods are similar to the evaluate() and execute() methods in Interpreter
    private void resolve(Statement statement) {
        statement.accept(this);
    }

    // Using the visitor pattern. These methods are similar to the evaluate() and execute() methods in Interpreter
    private void resolve(Expression expression) {
        expression.accept(this);
    }

    private void beginScope() {
        scopes.push(new HashMap<String, Boolean>());
    }

    private void endScope() {
        scopes.pop();
    }

    /**
     * adds the variable to the innermost scope so that it shadows any outer one and so that we know the variable exists
     * @param name the name of the variable
     */
    private void declare(TokenId name) {
        if (scopes.isEmpty()) return;

        Map<String, Boolean> scope = scopes.peek();
        if (scope.containsKey(name.getId())) {
            throw new SemanticException(
                    "Already a variable with this name in this scope.");
        }

        scope.put(name.getId(), false);
    }

    /**
     *  Set the variable’s value in the scope map to true to mark it as fully initialized and available for use. It’s alive!
     * @param name the name of the variable to define.
     */
    private void define(TokenId name) {
        if (scopes.isEmpty()) return;
        scopes.peek().put(name.getId(), true);
    }

    private void resolveLocal(Expression expr, TokenId name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(name.getId())) {
                interpreter.resolve(expr, scopes.size() - 1 - i);
                return;
            }
        }
    }

    private void resolveFunction(FunctionStatement function, FunctionType type) {
        FunctionType enclosingFunction = currentFunction;
        currentFunction = type;

        beginScope();
        for (TokenId param : function.params()) {
            declare(param);
            define(param);
        }
        resolve(function.body());
        endScope();
        currentFunction = enclosingFunction;
    }
}
