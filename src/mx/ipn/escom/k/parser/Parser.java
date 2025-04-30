package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.KLogger;
import mx.ipn.escom.k.core.exception.KError;
import mx.ipn.escom.k.core.expression.Expression;
import mx.ipn.escom.k.core.statement.Statement;
import mx.ipn.escom.k.core.AST;
import mx.ipn.escom.k.core.expression.*;
import mx.ipn.escom.k.core.statement.*;
import mx.ipn.escom.k.scanner.Scanner;
import mx.ipn.escom.k.core.token.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private final Scanner scanner;
    private Token lookahead;
    private Token previous;
    private final KLogger logger;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.lookahead = scanner.next();
        this.previous = null;

        logger = KLogger.getInstance();
    }

    public AST parse() {
        List<Statement> statements = program();

        if (lookahead.getTokenName() != TokenName.EOF) {
            error(lookahead, "An error found in the code.");
        }

        return new AST(statements);
    }

    private List<Statement> program() {
        List<Statement> statements = new ArrayList<>();

        try {
            declaration(statements);
        }
        catch (KError e) {
            synchronize();
            return null;
        }
        return statements;
    }

    private void declaration(List<Statement> statements) throws KError{
        switch (lookahead.getTokenName()) {
            case CLASS:
                Statement classDecl = classDeclaration();
                statements.add(classDecl);
                declaration(statements);
                break;
            case FUN:
                Statement funDecl = funDeclaration();
                statements.add(funDecl);
                declaration(statements);
                break;
            case VAR:
                Statement varDecl = varDeclaration();
                statements.add(varDecl);
                declaration(statements);
                break;
            case FOR:
            case IF:
            case WHILE:
            case PRINT:
            case RETURN:
            case LEFT_BRACE:
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
            case SUPER:
            case THIS:
                Statement stmt = statement();
                statements.add(stmt);
                declaration(statements);
                break;
        }
    }

    private Statement classDeclaration() throws KError{
        match(TokenName.CLASS);
        match(TokenName.IDENTIFIER);
        Token name = previous();
        VariableExpression superClass = classInher();
        match(TokenName.LEFT_BRACE);
        List<Statement> attributesAndMethods = new ArrayList<>();
        classElement(attributesAndMethods);
        match(TokenName.RIGHT_BRACE);

        return new ClassStatement(name, superClass, attributesAndMethods);
    }

    private VariableExpression classInher() throws KError{
        if (lookahead.getTokenName() == TokenName.EXTENDS) {
            match(TokenName.EXTENDS);
            match(TokenName.IDENTIFIER);
            TokenId nameSuperClass = (TokenId) previous();
            return new VariableExpression(nameSuperClass);
        }
        return null;
    }

    private void classElement(List<Statement> attributesAndMethods) throws KError{
        if(lookahead.getTokenName() == TokenName.VAR){
            Statement attribute = varDeclaration();
            attributesAndMethods.add(attribute);
            classElement(attributesAndMethods);
        }
        else if(lookahead.getTokenName() == TokenName.FUN){
            Statement method = funDeclaration();
            attributesAndMethods.add(method);
            classElement(attributesAndMethods);
        }
    }

    private Statement funDeclaration() throws KError{
        match(TokenName.FUN);
        match(TokenName.IDENTIFIER);
        TokenId name = (TokenId)previous();
        match(TokenName.LEFT_PAREN);
        List<TokenId> parameters = parameters();
        match(TokenName.RIGHT_PAREN);
        Statement body = block();

        return new FunctionStatement(name, parameters, (BlockStatement) body);
    }

    private Statement varDeclaration() throws KError{
        match(TokenName.VAR);
        match(TokenName.IDENTIFIER);
        TokenId name = (TokenId) previous();
        Expression init = varInit();
        match(TokenName.SEMICOLON);

        return new VarStatement(name, init);
    }

    private Expression varInit() throws KError{
        if (lookahead.getTokenName() == TokenName.EQUAL) {
            match(TokenName.EQUAL);
            return expression();
        }
        return null;
    }

    private Statement statement() throws KError{
        switch (lookahead.getTokenName()) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
            case SUPER:
            case THIS:
                return exprStatement();
            case FOR:
                return forStatement();
            case IF:
                return ifStatement();
            case WHILE:
                return whileStatement();
            case PRINT:
                return printStatement();
            case RETURN:
                return returnStatement();
            case LEFT_BRACE:
                return block();
            default:
                throw error(lookahead, "Statement expected.");
        }
    }

    private Statement exprStatement() throws KError{
        Expression expr = expression();
        match(TokenName.SEMICOLON);
        return new ExpressionStatement(expr);
    }

    private Statement forStatement() throws KError{
        match(TokenName.FOR);
        Token keyword = previous();
        match(TokenName.LEFT_PAREN);
        Statement initializer = forStatementInit();
        Expression condition = forStatementCondition();
        Expression increment = forStatementIncrease();
        match(TokenName.RIGHT_PAREN);
        Statement body = statement();

        // "Desugar" increment
        if (increment != null) {
            body = new BlockStatement(
                    Arrays.asList(
                            body,
                            new ExpressionStatement(increment)
                    )
            );
        }

        // "Desugar" condition
        if (condition == null) {
            condition = new LiteralExpression(true);
        }
        body = new LoopStatement(keyword, condition, body);

        // "Desugar" initialization
        if (initializer != null) {
            body = new BlockStatement(Arrays.asList(initializer, body));
        }
        return body;
    }

    private Statement forStatementInit() throws KError{
        switch (lookahead.getTokenName()) {
            case VAR:
                return varDeclaration();
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
            case SUPER:
            case THIS:
                Statement stmt = exprStatement();
                match(TokenName.SEMICOLON);
                return stmt;
            case SEMICOLON:
                match(TokenName.SEMICOLON);
                return null;
            default:
                throw error(lookahead, "A declaration or expression was expected.");
        }

    }

    private Expression forStatementCondition() throws KError{
        switch (lookahead.getTokenName()) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
            case SUPER:
            case THIS:
                Expression expr = expression();
                match(TokenName.SEMICOLON);
                return expr;
            case SEMICOLON:
                match(TokenName.SEMICOLON);
                return null;
            default:
                throw error(lookahead, "A condition or ';' was expected.");
        }

    }

    private Expression forStatementIncrease() throws KError{
        switch (lookahead.getTokenName()) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
            case SUPER:
            case THIS:
                return expression();
        }
        return null;
    }

    private Statement ifStatement() throws KError{
        match(TokenName.IF);
        Token keyword = previous();
        match(TokenName.LEFT_PAREN);
        Expression condition = expression();
        match(TokenName.RIGHT_PAREN);
        Statement thenBranch = statement();
        Statement elseBranch = elseStatement();

        return new IfStatement(keyword, condition, thenBranch, elseBranch);
    }

    private Statement elseStatement() throws KError{
        if (lookahead.getTokenName() == TokenName.ELSE) {
            match(TokenName.ELSE);
            return statement();
        }
        return null;
    }

    private Statement whileStatement() throws KError{
        match(TokenName.WHILE);
        Token keyword = previous();
        match(TokenName.LEFT_PAREN);
        Expression condition = expression();
        match(TokenName.RIGHT_PAREN);
        Statement body = statement();

        return new LoopStatement(keyword, condition, body);
    }

    private Statement printStatement() throws KError{
        match(TokenName.PRINT);
        Expression expr = expression();
        match(TokenName.SEMICOLON);

        return new PrintStatement(expr);
    }

    private Statement returnStatement() throws KError{
        match(TokenName.RETURN);
        Token keyword = previous();
        Expression value = returnExpressionOptional();
        match(TokenName.SEMICOLON);

        return new ReturnStatement(keyword, value);
    }

    private Expression returnExpressionOptional() throws KError{
        switch (lookahead.getTokenName()) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
            case SUPER:
            case THIS:
                return expression();
        }
        return null;
    }

    private Statement block() throws KError{
        match(TokenName.LEFT_BRACE);
        List<Statement> statements = new ArrayList<>();
        declaration(statements);
        match(TokenName.RIGHT_BRACE);

        return new BlockStatement(statements);
    }

    /*
    Bloque de expresiones
     */

    private Expression expression() throws KError{
        switch (lookahead.getTokenName()) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
            case SUPER:
            case THIS:
                return assignment();
            default:
                throw error(lookahead, "An expression was expected.");

        }
    }

    private Expression assignment() throws KError{
        Expression expr = logicOr();
        return assignmentOptional(expr);
    }

    private Expression assignmentOptional(Expression expr) throws KError{
        if (lookahead.getTokenName() == TokenName.EQUAL) {
            match(TokenName.EQUAL);
            Token equal = previous();
            Expression value = expression();

            if (expr instanceof VariableExpression) {
                TokenId name = ((VariableExpression) expr).name();
                return new AssignmentExpression(name, value);
            } else if (expr instanceof GetExpression) {
                GetExpression get = (GetExpression) expr;
                return new SetExpression(get.object(), get.name(), value);
            }

            throw error(equal,"Invalid assignment target.");

        }
        return expr;
    }

    private Expression logicOr() throws KError{
        Expression expr = logicAnd();
        return logicOrPrime(expr);
    }

    private Expression logicOrPrime(Expression left) throws KError{
        if (lookahead.getTokenName() == TokenName.OR) {
            match(TokenName.OR);
            Token operator = previous();
            Expression right = logicAnd();
            Expression expr = new LogicalExpression(left, operator, right);
            return logicOrPrime(expr);
        }

        return left;
    }

    private Expression logicAnd() throws KError{
        Expression expr = equality();
        return logicAndPrime(expr);
    }

    private Expression logicAndPrime(Expression left) throws KError{
        if (lookahead.getTokenName() == TokenName.AND) {
            match(TokenName.AND);
            Token operator = previous();
            Expression right = equality();
            Expression expr = new LogicalExpression(left, operator, right);
            return logicAndPrime(expr);
        }

        return left;
    }

    private Expression equality() throws KError{
        Expression expr = comparison();
        return equalityPrime(expr);
    }

    private Expression equalityPrime(Expression left) throws KError{
        switch (lookahead.getTokenName()) {
            case BANG_EQUAL:
            case EQUAL_EQUAL:
                match(lookahead.getTokenName()); //!= o ==
                Token operator = previous();
                Expression right = comparison();
                Expression expr = new RelationalExpression(left, operator, right);
                return equalityPrime(expr);
        }
        return left;
    }

    private Expression comparison() throws KError{
        Expression expr = term();
        return comparisonPrime(expr);
    }

    private Expression comparisonPrime(Expression left) throws KError{
        switch (lookahead.getTokenName()) {
            case GREATER:
            case GREATER_EQUAL:
            case LESS:
            case LESS_EQUAL:
                match(lookahead.getTokenName()); // <, <=, >, >=
                Token operator = previous();
                Expression right = term();
                Expression expr = new RelationalExpression(left, operator, right);
                return comparisonPrime(expr);
        }

        return left;
    }

    private Expression term() throws KError{
        Expression expr = factor();
        return termPrime(expr);
    }

    private Expression termPrime(Expression left) throws KError{
        switch (lookahead.getTokenName()) {
            case MINUS:
            case PLUS:
                match(lookahead.getTokenName()); // MINUS o PLUS
                Token operator = previous();
                Expression right = factor();
                Expression expr = new ArithmeticExpression(left, operator, right);
                return termPrime(expr);
        }
        return left;
    }

    private Expression factor() throws KError{
        Expression expr = unary();
        return factorPrime(expr);
    }

    private Expression factorPrime(Expression left) throws KError{
        switch (lookahead.getTokenName()) {
            case SLASH:
            case STAR:
                match(lookahead.getTokenName()); // SLASH o STAR
                Token operator = previous();
                Expression right = unary();
                Expression expr = new ArithmeticExpression(left, operator, right);
                return factorPrime(expr);
        }
        return left;
    }

    private Expression unary() throws KError{
        switch (lookahead.getTokenName()) {
            case BANG:
                match(TokenName.BANG);
                Token operator = previous();
                Expression expr = unary();
                return new UnaryExpression(operator, expr);
            case MINUS:
                match(TokenName.MINUS);
                operator = previous();
                expr = unary();
                return new UnaryExpression(operator, expr);
            default:
                return call();
        }
    }

    private Expression call() throws KError{
        Expression expr = primary();

        switch (lookahead.getTokenName()) {
            case LEFT_PAREN:
            case DOT:
                expr = callPrime(expr);
        }

        return expr;
    }

    private Expression callPrime(Expression expr) throws KError{
        switch (lookahead.getTokenName()) {
            case LEFT_PAREN:
                match(TokenName.LEFT_PAREN);
                List<Expression> lstArguments = arguments();
                match(TokenName.RIGHT_PAREN);
                TokenPunctuationMarks paren = (TokenPunctuationMarks) previous();
                Expression exprCall = new CallExpression(expr, paren, lstArguments);
                return callPrime(exprCall);
            case DOT:
                match(TokenName.DOT);
                match(TokenName.IDENTIFIER);
                Token name = previous();
                Expression exprGet = new GetExpression(expr, name);
                return callPrime(exprGet);
        }
        return expr;
    }

    private Expression primary() throws KError{
        switch (lookahead.getTokenName()) {
            case TRUE:
                match(TokenName.TRUE);
                return new LiteralExpression(true);
            case FALSE:
                match(TokenName.FALSE);
                return new LiteralExpression(false);
            case NULL:
                match(TokenName.NULL);
                return new LiteralExpression(null);
            case THIS:
                match(TokenName.THIS);
                return new ThisExpression();
            case NUMBER:
                match(TokenName.NUMBER);
                return new LiteralExpression( ((TokenNumber)previous()).value);
            case STRING:
                match(TokenName.STRING);
                return new LiteralExpression(((TokenString)previous()).value);
            case IDENTIFIER:
                match(TokenName.IDENTIFIER);
                TokenId tokenId = (TokenId) previous();
                return new VariableExpression(tokenId);
            case LEFT_PAREN:
                match(TokenName.LEFT_PAREN);
                Expression expr = expression();
                match(TokenName.RIGHT_PAREN);
                return new GroupingExpression(expr);
            case SUPER:
                match(TokenName.SUPER);
                match(TokenName.DOT);
                match(TokenName.IDENTIFIER);
                return new SuperExpression(previous());
        }
        throw error(lookahead,"Token not valid.");

    }

    /*
    Bloque de funciones auxiliares
     */

    private List<TokenId> parameters() throws KError{
        List<TokenId> params = new ArrayList<>();
        if (lookahead.getTokenName() == TokenName.IDENTIFIER) {
            match(TokenName.IDENTIFIER);
            TokenId name = (TokenId) previous();
            params.add(name);
            parametersPrime(params);
        }

        return params;
    }

    private void parametersPrime(List<TokenId> params) throws KError{
        if (lookahead.getTokenName() == TokenName.COMMA) {
            match(TokenName.COMMA);
            match(TokenName.IDENTIFIER);
            TokenId name = (TokenId) previous();
            params.add(name);
            parametersPrime(params);
        }
    }

    private List<Expression> arguments() throws KError{
        List<Expression> lstArguments = new ArrayList<>();

        switch (lookahead.getTokenName()) {
            case BANG:
            case MINUS:
            case TRUE:
            case FALSE:
            case NULL:
            case NUMBER:
            case STRING:
            case IDENTIFIER:
            case LEFT_PAREN:
            case SUPER:
            case THIS:
                Expression expr = expression();
                lstArguments.add(expr);
                argumentsPrime(lstArguments);
                break;
        }
        return lstArguments;
    }

    private void argumentsPrime(List<Expression> lstArguments) {
        if (lookahead.getTokenName() == TokenName.COMMA) {
            match(TokenName.COMMA);
            Expression expr = expression();
            lstArguments.add(expr);
            argumentsPrime(lstArguments);
        }
    }

    private void match(TokenName tt) throws KError{
        if (lookahead.getTokenName() == tt) {
            advance();
        } else {
            throw error(lookahead,"Expected " + tt);
        }
    }

    private Token previous() {
        return this.previous;
    }

    private KError error(Token token, String message) {
        logger.error(token, message);
        return new KError();
    }

    /**
     * This method implements the recovery technique 'panic mode'
     *
     * Synchronizes the parser state to prevent cascading
     * errors by advancing through tokens until a logical
     * stopping point or synchronizing token is encountered.
     *
     * The method skips over tokens until it detects a token that
     * marks the statement boundary, in this case a 'semicolon' or
     * the beginning of a new construct (e.g., 'class', 'fun', etc.).
     * It ensures that the parser can continue parsing without being
     * affected by errors in preceding statements or expressions.
     *
     * Behavior:
     * - Advances over tokens, starting from the current state.
     * - If a semicolon (';') is encountered, it returns immediately.
     * - If a token corresponding to the beginning of a new
     *   statement or construct is encountered
     *   (e.g., CLASS, FUN, VAR, etc.), it also returns.
     * - Continues advancing until reaching the end-of-file (EOF) or a synchronization point.
     *
     * This method is designed to maintain the integrity of the
     * parser state during error recovery.
     */
    private void synchronize() {
        // Consume the token that raise the error
        advance();

        while (!isAtEnd()) {

            // Stop the while when a statement has ended
            if (previous.getTokenName() == TokenName.SEMICOLON){
                return;
            }

            // This method is called from the method 'declaration'.
            // Those 'token name' are some elements of the set
            // 'first(Declaration)'
            // Stop when start a new statement
            switch (lookahead.getTokenName()) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                    return;
            }

            // skip the tokens until a synchronization token
            advance();
        }
    }

    private void advance() {
        previous = lookahead;
        lookahead = scanner.next();
    }

    private boolean isAtEnd() {
        return lookahead.getTokenName() == TokenName.EOF;
    }

}
