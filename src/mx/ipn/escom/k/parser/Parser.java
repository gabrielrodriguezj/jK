package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.core.Expression;
import mx.ipn.escom.k.core.Statement;
import mx.ipn.escom.k.core.exception.ParserException;
import mx.ipn.escom.k.core.AST;
import mx.ipn.escom.k.core.expression.*;
import mx.ipn.escom.k.core.statement.*;
import mx.ipn.escom.k.scanner.Scanner;
import mx.ipn.escom.k.token.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private Scanner scanner;
    private Token preanalisis;
    private Token previous;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.preanalisis = scanner.next();
        this.previous = null;
    }

    public AST parse() throws ParserException {
        List<Statement> statements = program();

        if (preanalisis.getTokenName() != TokenName.EOF) {
            String message = "An error found in the code";
            throw new ParserException(message);
        }

        return new AST(statements);
    }

    private List<Statement> program() throws ParserException {
        List<Statement> statements = new ArrayList<>();

        declaration(statements);

        return statements;
    }

    private void declaration(List<Statement> statements) throws ParserException {
        switch (preanalisis.getTokenName()) {
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

    private Statement classDeclaration() throws ParserException {
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

    private VariableExpression classInher() throws ParserException {
        if (preanalisis.getTokenName() == TokenName.EXTENDS) {
            match(TokenName.EXTENDS);
            match(TokenName.IDENTIFIER);
            TokenId nameSuperClass = (TokenId) previous();
            return new VariableExpression(nameSuperClass);
        }
        return null;
    }

    private void classElement(List<Statement> attributesAndMethods) throws ParserException {
        if(preanalisis.getTokenName() == TokenName.VAR){
            Statement attribute = varDeclaration();
            attributesAndMethods.add(attribute);
            classElement(attributesAndMethods);
        }
        else if(preanalisis.getTokenName() == TokenName.FUN){
            Statement method = funDeclaration();
            attributesAndMethods.add(method);
            classElement(attributesAndMethods);
        }
    }

    private Statement funDeclaration() throws ParserException {
        match(TokenName.FUN);
        match(TokenName.IDENTIFIER);
        TokenId name = (TokenId)previous();
        match(TokenName.LEFT_PAREN);
        List<Token> parameters = parameters();
        match(TokenName.RIGHT_PAREN);
        Statement body = block();

        return new FunctionStatement(name, parameters, (BlockStatement) body);
    }

    private Statement varDeclaration() throws ParserException {
        match(TokenName.VAR);
        match(TokenName.IDENTIFIER);
        Token name = previous();
        Expression init = varInit();
        match(TokenName.SEMICOLON);

        return new VarStatement(name, init);
    }

    private Expression varInit() throws ParserException {
        if (preanalisis.getTokenName() == TokenName.EQUAL) {
            match(TokenName.EQUAL);
            return expression();
        }
        return null;
    }

    private Statement statement() throws ParserException {
        switch (preanalisis.getTokenName()) {
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
                String message = "Error in the line " +
                        preanalisis.getLine() +
                        " close of token " +
                        preanalisis.getTokenName();
                throw new ParserException(message);
        }
    }

    private Statement exprStatement() throws ParserException {
        Expression expr = expression();
        match(TokenName.SEMICOLON);
        return new ExpressionStatement(expr);
    }

    private Statement forStatement() throws ParserException {
        match(TokenName.FOR);
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
        body = new LoopStatement(condition, body);

        // "Desugar" initialization
        if (initializer != null) {
            body = new BlockStatement(Arrays.asList(initializer, body));
        }
        return body;
    }

    private Statement forStatementInit() throws ParserException {
        switch (preanalisis.getTokenName()) {
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
                String message = "Error in the line " +
                        preanalisis.getLine() +
                        " close of " + preanalisis.getTokenName() +
                        " An declaration or expression was expected";
                throw new ParserException(message);
        }

    }

    private Expression forStatementCondition() throws ParserException {
        switch (preanalisis.getTokenName()) {
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
                String message = "Error in the line " +
                        preanalisis.getLine() +
                        " close of " + preanalisis.getTokenName() +
                        " A condition was expected";
                throw new ParserException(message);
        }

    }

    private Expression forStatementIncrease() throws ParserException {
        switch (preanalisis.getTokenName()) {
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

    private Statement ifStatement() throws ParserException {
        match(TokenName.IF);
        match(TokenName.LEFT_PAREN);
        Expression condition = expression();
        match(TokenName.RIGHT_PAREN);
        Statement thenBranch = statement();
        Statement elseBranch = elseStatement();

        return new IfStatement(condition, thenBranch, elseBranch);
    }

    private Statement elseStatement() throws ParserException {
        if (preanalisis.getTokenName() == TokenName.ELSE) {
            match(TokenName.ELSE);
            return statement();
        }
        return null;
    }

    private Statement whileStatement() throws ParserException {
        match(TokenName.WHILE);
        match(TokenName.LEFT_PAREN);
        Expression condition = expression();
        match(TokenName.RIGHT_PAREN);
        Statement body = statement();

        return new LoopStatement(condition, body);
    }

    private Statement printStatement() throws ParserException {
        match(TokenName.PRINT);
        Expression expr = expression();
        match(TokenName.SEMICOLON);

        return new PrintStatement(expr);
    }

    private Statement returnStatement() throws ParserException {
        match(TokenName.RETURN);
        Expression value = returnExpressionOptional();
        match(TokenName.SEMICOLON);

        return new ReturnStatement(value);
    }

    private Expression returnExpressionOptional() throws ParserException {
        switch (preanalisis.getTokenName()) {
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

    private Statement block() throws ParserException {
        match(TokenName.LEFT_BRACE);
        List<Statement> statements = new ArrayList<>();
        declaration(statements);
        match(TokenName.RIGHT_BRACE);

        return new BlockStatement(statements);
    }

    /*
    Bloque de expresiones
     */

    private Expression expression() throws ParserException {
        switch (preanalisis.getTokenName()) {
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
                String message = "Error in the line " +
                        preanalisis.getLine() +
                        " close of " + preanalisis.getTokenName() +
                        " . An expression was expected";
                throw new ParserException(message);
        }
    }

    private Expression assignment() throws ParserException {
        Expression expr = logicOr();
        return assignmentOptional(expr);
    }

    private Expression assignmentOptional(Expression expr) throws ParserException {
        if (preanalisis.getTokenName() == TokenName.EQUAL) {
            match(TokenName.EQUAL);
            Expression value = expression();

            if (expr instanceof VariableExpression) {
                TokenId name = ((VariableExpression) expr).name();
                return new AssignmentExpression(name, value);
            } else if (expr instanceof GetExpression) {
                GetExpression get = (GetExpression) expr;
                return new SetExpression(get.object(), get.name(), value);
            }

            throw new ParserException("Assignment expression bad formed. Left side is non assignable");

        }
        return expr;
    }

    private Expression logicOr() throws ParserException {
        Expression expr = logicAnd();
        return logicOrPrime(expr);
    }

    private Expression logicOrPrime(Expression left) throws ParserException {
        if (preanalisis.getTokenName() == TokenName.OR) {
            match(TokenName.OR);
            Token operator = previous();
            Expression right = logicAnd();
            Expression expr = new LogicalExpression(left, operator, right);
            return logicOrPrime(expr);
        }

        return left;
    }

    private Expression logicAnd() throws ParserException {
        Expression expr = equality();
        return logicAndPrime(expr);
    }

    private Expression logicAndPrime(Expression left) throws ParserException {
        if (preanalisis.getTokenName() == TokenName.AND) {
            match(TokenName.AND);
            Token operator = previous();
            Expression right = equality();
            Expression expr = new LogicalExpression(left, operator, right);
            return logicAndPrime(expr);
        }

        return left;
    }

    private Expression equality() throws ParserException {
        Expression expr = comparison();
        return equalityPrime(expr);
    }

    private Expression equalityPrime(Expression left) throws ParserException {
        switch (preanalisis.getTokenName()) {
            case BANG_EQUAL:
            case EQUAL_EQUAL:
                match(preanalisis.getTokenName()); //!= o ==
                Token operator = previous();
                Expression right = comparison();
                Expression expr = new RelationalExpression(left, operator, right);
                return equalityPrime(expr);
        }
        return left;
    }

    private Expression comparison() throws ParserException {
        Expression expr = term();
        return comparisonPrime(expr);
    }

    private Expression comparisonPrime(Expression left) throws ParserException {
        switch (preanalisis.getTokenName()) {
            case GREATER:
            case GREATER_EQUAL:
            case LESS:
            case LESS_EQUAL:
                match(preanalisis.getTokenName()); // <, <=, >, >=
                Token operator = previous();
                Expression right = term();
                Expression expr = new RelationalExpression(left, operator, right);
                return comparisonPrime(expr);
        }

        return left;
    }

    private Expression term() throws ParserException {
        Expression expr = factor();
        return termPrime(expr);
    }

    private Expression termPrime(Expression left) throws ParserException {
        switch (preanalisis.getTokenName()) {
            case MINUS:
            case PLUS:
                match(preanalisis.getTokenName()); // MINUS o PLUS
                Token operator = previous();
                Expression right = factor();
                Expression expr = new ArithmeticExpression(left, operator, right);
                return termPrime(expr);
        }
        return left;
    }

    private Expression factor() throws ParserException {
        Expression expr = unary();
        return factorPrime(expr);
    }

    private Expression factorPrime(Expression left) throws ParserException {
        switch (preanalisis.getTokenName()) {
            case SLASH:
            case STAR:
                match(preanalisis.getTokenName()); // SLASH o STAR
                Token operator = previous();
                Expression right = unary();
                Expression expr = new ArithmeticExpression(left, operator, right);
                return factorPrime(expr);
        }
        return left;
    }

    private Expression unary() throws ParserException {
        switch (preanalisis.getTokenName()) {
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

    private Expression call() throws ParserException {
        Expression expr = primary();

        switch (preanalisis.getTokenName()) {
            case LEFT_PAREN:
            case DOT:
                expr = callPrime(expr);
        }

        return expr;
    }

    private Expression callPrime(Expression expr) throws ParserException {
        switch (preanalisis.getTokenName()) {
            case LEFT_PAREN:
                match(TokenName.LEFT_PAREN);
                List<Expression> lstArguments = arguments();
                match(TokenName.RIGHT_PAREN);
                Expression exprCall = new CallFunctionExpression(expr, lstArguments);
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

    private Expression primary() throws ParserException {
        switch (preanalisis.getTokenName()) {
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
        throw new ParserException("Expression not expected");
    }

    /*
    Bloque de funciones auxiliares
     */

    private List<Token> parameters() throws ParserException {
        List<Token> params = new ArrayList<>();
        if (preanalisis.getTokenName() == TokenName.IDENTIFIER) {
            match(TokenName.IDENTIFIER);
            Token name = previous();
            params.add(name);
            parametersPrime(params);
        }

        return params;
    }

    private void parametersPrime(List<Token> params) throws ParserException {
        if (preanalisis.getTokenName() == TokenName.COMMA) {
            match(TokenName.COMMA);
            match(TokenName.IDENTIFIER);
            Token name = previous();
            params.add(name);
            parametersPrime(params);
        }
    }

    private List<Expression> arguments() throws ParserException {
        List<Expression> lstArguments = new ArrayList<>();

        switch (preanalisis.getTokenName()) {
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

    private void argumentsPrime(List<Expression> lstArguments) throws ParserException {
        if (preanalisis.getTokenName() == TokenName.COMMA) {
            match(TokenName.COMMA);
            Expression expr = expression();
            lstArguments.add(expr);
            argumentsPrime(lstArguments);
        }
    }

    private void match(TokenName tt) throws ParserException {
        if (preanalisis.getTokenName() == tt) {
            previous = preanalisis;
            preanalisis = scanner.next();
        } else {
            String message = "Error in the line " +
                    preanalisis.getLine() +
                    ". Expected " + tt +
                    " but founded " + preanalisis.getTokenName();
            throw new ParserException(message);
        }
    }

    private Token previous() {
        return this.previous;
    }
}
