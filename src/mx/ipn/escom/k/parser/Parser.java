package mx.ipn.escom.k.parser;

import mx.ipn.escom.k.exception.ParserException;
import mx.ipn.escom.k.interpreter.AST;
import mx.ipn.escom.k.tools.TipoToken;
import mx.ipn.escom.k.tools.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int i = 0;
    private Token preanalisis;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.preanalisis = tokens.get(i);
    }

    public AST parse() throws ParserException {
        List<Statement> statements = program();

        if (preanalisis.getTipo() != TipoToken.EOF) {
            String message = "Se encontró un error en el programa";
            throw new ParserException(message);
        }

        return new AST(statements);
    }

    private List<Statement> program() throws ParserException {
        List<Statement> statements = new ArrayList<>();
        switch (preanalisis.getTipo()) {
            case CLASS:
            case FUN:
            case VAR:
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
                declaration(statements);
        }
        return statements;
    }

    private void declaration(List<Statement> statements) throws ParserException {
        switch (preanalisis.getTipo()) {
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
        if (preanalisis.getTipo() == TipoToken.CLASS) {
            match(TipoToken.CLASS);
            match(TipoToken.IDENTIFIER);
            Token name = previous();
            ExprVariable superClass = classInher();
            match(TipoToken.LEFT_BRACE);
            List<StmtFunction> methods = new ArrayList<>();
            functions(methods);
            match(TipoToken.RIGHT_BRACE);

            return new StmtClass(name, superClass, methods);
        } else {
            String message = "Error en la posición " +
                    preanalisis.getPosition() +
                    ". Se esperaba el token class pero se encontró " +
                    preanalisis.getLexema();
            throw new ParserException(message);
        }
    }

    private ExprVariable classInher() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.EXTENDS) {
            match(TipoToken.EXTENDS);
            match(TipoToken.IDENTIFIER);
            Token nameSuperClass = previous();
            return new ExprVariable(nameSuperClass);
        }
        return null;
    }

    private Statement funDeclaration() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.FUN) {
            match(TipoToken.FUN);
            return function();
        } else {
            String message = "Error en la posición " +
                    preanalisis.getPosition().getLine() +
                    ". Se esperaba el token fun pero se encontró " +
                    preanalisis.getLexema();
            throw new ParserException(message);
        }
    }

    private Statement varDeclaration() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.VAR) {
            match(TipoToken.VAR);
            match(TipoToken.IDENTIFIER);
            Token name = previous();
            Expression init = varInit();
            match(TipoToken.SEMICOLON);

            return new StmtVar(name, init);
        } else {
            String message = "Error en la posición " +
                    preanalisis.getPosition().getLine() +
                    ". Se esperaba el token var pero se encontró " +
                    preanalisis.getLexema();
            throw new ParserException(message);
        }
    }

    private Expression varInit() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.EQUAL) {
            match(TipoToken.EQUAL);
            return expression();
        }
        return null;
    }

    private Statement statement() throws ParserException {
        switch (preanalisis.getTipo()) {
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
                String message = "Error en la posición " +
                        preanalisis.getPosition() +
                        " cerca de " +
                        preanalisis.getLexema();
                throw new ParserException(message);
        }
    }

    private Statement exprStatement() throws ParserException {
        switch (preanalisis.getTipo()) {
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
                match(TipoToken.SEMICOLON);
                return new StmtExpression(expr);
            default:
                String message = "Error en la posición " +
                        preanalisis.getPosition() +
                        " cerca de " +
                        preanalisis.getLexema();
                throw new ParserException(message);
        }
    }

    private Statement forStatement() throws ParserException {
        match(TipoToken.FOR);
        match(TipoToken.LEFT_PAREN);
        Statement initializer = forStatementInit();
        Expression condition = forStatementCondition();
        Expression increment = forStatementIncrease();
        match(TipoToken.RIGHT_PAREN);
        Statement body = statement();

        // "Desugar" incremento
        if (increment != null) {
            body = new StmtBlock(
                    Arrays.asList(
                            body,
                            new StmtExpression(increment)
                    )
            );
        }

        // "Desugar" condición
        if (condition == null) {
            condition = new ExprLiteral(true);
        }
        body = new StmtLoop(condition, body);

        // "Desugar" inicialización
        if (initializer != null) {
            body = new StmtBlock(Arrays.asList(initializer, body));
        }
        return body;
    }

    private Statement forStatementInit() throws ParserException {
        switch (preanalisis.getTipo()) {
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
                match(TipoToken.SEMICOLON);
                return stmt;
            case SEMICOLON:
                match(TipoToken.SEMICOLON);
                return null;
            default:
                String message = "Error en la posición " +
                        preanalisis.getPosition() +
                        " cerca de " + preanalisis.getLexema() +
                        " se esperaba una declaración o una expresión";
                throw new ParserException(message);
        }

    }

    private Expression forStatementCondition() throws ParserException {
        switch (preanalisis.getTipo()) {
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
                match(TipoToken.SEMICOLON);
                return expr;
            case SEMICOLON:
                match(TipoToken.SEMICOLON);
                return null;
            default:
                String message = "Error en la posición " +
                        preanalisis.getPosition() +
                        " cerca de " + preanalisis.getLexema() +
                        " se esperaba una condición";
                throw new ParserException(message);
        }

    }

    private Expression forStatementIncrease() throws ParserException {
        switch (preanalisis.getTipo()) {
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
        match(TipoToken.IF);
        match(TipoToken.LEFT_PAREN);
        Expression condition = expression();
        match(TipoToken.RIGHT_PAREN);
        Statement thenBranch = statement();
        Statement elseBranch = elseStatement();

        return new StmtIf(condition, thenBranch, elseBranch);
    }

    private Statement elseStatement() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.ELSE) {
            match(TipoToken.ELSE);
            return statement();
        }
        return null;
    }

    private Statement whileStatement() throws ParserException {
        match(TipoToken.WHILE);
        match(TipoToken.LEFT_PAREN);
        Expression condition = expression();
        match(TipoToken.RIGHT_PAREN);
        Statement body = statement();

        return new StmtLoop(condition, body);
    }

    private Statement printStatement() throws ParserException {
        match(TipoToken.PRINT);
        Expression expr = expression();
        match(TipoToken.SEMICOLON);

        return new StmtPrint(expr);
    }

    private Statement returnStatement() throws ParserException {
        match(TipoToken.RETURN);
        Expression value = returnExpressionOptional();
        match(TipoToken.SEMICOLON);

        return new StmtReturn(value);
    }

    private Expression returnExpressionOptional() throws ParserException {
        switch (preanalisis.getTipo()) {
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
        match(TipoToken.LEFT_BRACE);
        List<Statement> statements = new ArrayList<>();
        declaration(statements);
        match(TipoToken.RIGHT_BRACE);

        return new StmtBlock(statements);
    }


    /*
    Bloque de expresiones
     */
    private Expression expression() throws ParserException {
        switch (preanalisis.getTipo()) {
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
                String message = "Error en la posición " +
                        preanalisis.getPosition() +
                        " cerca de " + preanalisis.getLexema() +
                        " se esperaba una expressión";
                throw new ParserException(message);
        }
    }

    private Expression assignment() throws ParserException {
        Expression expr = logicOr();
        return assignmentOptional(expr);
    }

    private Expression assignmentOptional(Expression expr) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.EQUAL) {
            match(TipoToken.EQUAL);
            Expression value = expression();

            if (expr instanceof ExprVariable) {
                Token name = ((ExprVariable) expr).name;
                return new ExprAssign(name, value);
            } else if (expr instanceof ExprGet) {
                ExprGet get = (ExprGet) expr;
                return new ExprSet(get.object, get.name, value);
            }

            throw new ParserException("Asignación inválida del objetivo");

        }
        return expr;
    }

    private Expression logicOr() throws ParserException {
        Expression expr = logicAnd();
        return logicOr2(expr);
    }

    private Expression logicOr2(Expression left) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.OR) {
            match(TipoToken.OR);
            Token operator = previous();
            Expression right = logicAnd();
            Expression expr = new ExprLogical(left, operator, right);
            return logicOr2(expr);
        }

        return left;
    }

    private Expression logicAnd() throws ParserException {
        Expression expr = equality();
        return logicAnd2(expr);
    }

    private Expression logicAnd2(Expression left) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.AND) {
            match(TipoToken.AND);
            Token operator = previous();
            Expression right = equality();
            Expression expr = new ExprLogical(left, operator, right);
            return logicAnd2(expr);
        }

        return left;
    }

    private Expression equality() throws ParserException {
        Expression expr = comparison();
        return equality2(expr);
    }

    private Expression equality2(Expression left) throws ParserException {
        switch (preanalisis.getTipo()) {
            case BANG_EQUAL:
            case EQUAL_EQUAL:
                match(preanalisis.getTipo()); //!= o ==
                Token operator = previous();
                Expression right = comparison();
                Expression expr = new ExprRelational(left, operator, right);
                return equality2(expr);
        }
        return left;
    }

    private Expression comparison() throws ParserException {
        Expression expr = term();
        return comparison2(expr);
    }

    private Expression comparison2(Expression left) throws ParserException {
        switch (preanalisis.getTipo()) {
            case GREATER:
            case GREATER_EQUAL:
            case LESS:
            case LESS_EQUAL:
                match(preanalisis.getTipo()); // <, <=, >, >=
                Token operator = previous();
                Expression right = term();
                Expression expr = new ExprRelational(left, operator, right);
                return comparison2(expr);
        }

        return left;
    }

    private Expression term() throws ParserException {
        Expression expr = factor();
        return term2(expr);
    }

    private Expression term2(Expression left) throws ParserException {
        switch (preanalisis.getTipo()) {
            case MINUS:
            case PLUS:
                match(preanalisis.getTipo()); // MINUS o PLUS
                Token operator = previous();
                Expression right = factor();
                Expression expr = new ExprBinary(left, operator, right);
                return term2(expr);
        }
        return left;
    }

    private Expression factor() throws ParserException {
        Expression expr = unary();
        return factor2(expr);
    }

    private Expression factor2(Expression left) throws ParserException {
        switch (preanalisis.getTipo()) {
            case SLASH:
            case STAR:
                match(preanalisis.getTipo()); // SLASH o STAR
                Token operator = previous();
                Expression right = unary();
                Expression expr = new ExprBinary(left, operator, right);
                return factor2(expr);
        }
        return left;
    }

    private Expression unary() throws ParserException {
        switch (preanalisis.getTipo()) {
            case BANG:
                match(TipoToken.BANG);
                Token operator = previous();
                Expression expr = unary();
                return new ExprUnary(operator, expr);
            case MINUS:
                match(TipoToken.MINUS);
                operator = previous();
                expr = unary();
                return new ExprUnary(operator, expr);
            default:
                return call();
        }
    }

    private Expression call() throws ParserException {
        Expression expr = primary();

        switch (preanalisis.getTipo()) {
            case LEFT_PAREN:
            case DOT:
                expr = call2(expr);
        }

        return expr;
    }

    private Expression call2(Expression expr) throws ParserException {
        switch (preanalisis.getTipo()) {
            case LEFT_PAREN:
                match(TipoToken.LEFT_PAREN);
                List<Expression> lstArguments = argumentsOptional();
                match(TipoToken.RIGHT_PAREN);
                Expression exprCall = new ExprCallFunction(expr, lstArguments);
                return call2(exprCall);
            case DOT:
                match(TipoToken.DOT);
                match(TipoToken.IDENTIFIER);
                Token name = previous();
                Expression exprGet = new ExprGet(expr, name);
                return call2(exprGet);
        }
        return expr;
    }

    private Expression primary() throws ParserException {
        switch (preanalisis.getTipo()) {
            case TRUE:
                match(TipoToken.TRUE);
                return new ExprLiteral(true);
            case FALSE:
                match(TipoToken.FALSE);
                return new ExprLiteral(false);
            case NULL:
                match(TipoToken.NULL);
                return new ExprLiteral(null);
            case THIS:
                match(TipoToken.THIS);
                return new ExprThis();
            case NUMBER:
                match(TipoToken.NUMBER);
                return new ExprLiteral(previous().getLiteral());
            case STRING:
                match(TipoToken.STRING);
                return new ExprLiteral(previous().getLiteral());
            case IDENTIFIER:
                match(TipoToken.IDENTIFIER);
                return new ExprVariable(previous());
            case LEFT_PAREN:
                match(TipoToken.LEFT_PAREN);
                Expression expr = expression();
                match(TipoToken.RIGHT_PAREN);
                return new ExprGrouping(expr);
            case SUPER:
                match(TipoToken.SUPER);
                match(TipoToken.DOT);
                match(TipoToken.IDENTIFIER);
                return new ExprSuper(previous());
        }
        throw new ParserException("Expresión no esperada");
    }


    /*
    Bloque de funciones auxiliares
     */
    private StmtFunction function() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.IDENTIFIER) {
            match(TipoToken.IDENTIFIER);
            Token name = previous();
            match(TipoToken.LEFT_PAREN);
            List<Token> parameters = parametersOptional();
            match(TipoToken.RIGHT_PAREN);
            Statement body = block();

            return new StmtFunction(name, parameters, (StmtBlock) body);
        } else {
            String message = "Error en la posición " +
                    preanalisis.getPosition() +
                    " cerca de " +
                    preanalisis.getLexema() +
                    ". Se esperaba un identificador";
            throw new ParserException(message);
        }
    }

    private void functions(List<StmtFunction> functions) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.FUN) {
            StmtFunction fun = (StmtFunction) funDeclaration();
            functions.add(fun);
            functions(functions);
        }
    }

    private List<Token> parametersOptional() throws ParserException {
        if (preanalisis.getTipo() == TipoToken.IDENTIFIER) {
            List<Token> params = new ArrayList<>();
            parameters(params);
            return params;
        }

        return null;
    }

    private void parameters(List<Token> params) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.IDENTIFIER) {
            match(TipoToken.IDENTIFIER);
            Token name = previous();
            params.add(name);
            parameters2(params);
        } else {
            String message = "Error en la posición " +
                    preanalisis.getPosition() +
                    " cerca de " +
                    preanalisis.getLexema() +
                    ". Se esperaba un identificador";
            throw new ParserException(message);
        }
    }

    private void parameters2(List<Token> params) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.COMMA) {
            match(TipoToken.COMMA);
            match(TipoToken.IDENTIFIER);
            Token name = previous();
            params.add(name);
            parameters2(params);
        }
    }

    private List<Expression> argumentsOptional() throws ParserException {
        List<Expression> lstArguments = new ArrayList<>();

        switch (preanalisis.getTipo()) {
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
                arguments(lstArguments);
                break;
        }
        return lstArguments;
    }

    private void arguments(List<Expression> lstArguments) throws ParserException {
        if (preanalisis.getTipo() == TipoToken.COMMA) {
            match(TipoToken.COMMA);
            Expression expr = expression();
            lstArguments.add(expr);
            arguments(lstArguments);
        }
    }

    private void match(TipoToken tt) throws ParserException {
        if (preanalisis.getTipo() == tt) {
            i++;
            preanalisis = tokens.get(i);
        } else {
            String message = "Error en la línea " +
                    preanalisis.getPosition().getLine() +
                    ". Se esperaba " + tt +
                    " pero se encontró " + preanalisis.getTipo();
            throw new ParserException(message);
        }
    }

    private Token previous() {
        return this.tokens.get(i - 1);
    }

}
