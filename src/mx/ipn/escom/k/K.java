package mx.ipn.escom.k;

import mx.ipn.escom.k.core.AST;
import mx.ipn.escom.k.interpreter.Interpreter;
import mx.ipn.escom.k.parser.Parser;
import mx.ipn.escom.k.scanner.Scanner;
import mx.ipn.escom.k.resolver.Resolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class K {

    static KLogger logger = KLogger.getInstance();

    public static void main(String[] args) {
        if(args.length > 1) {
            System.out.println("More than the one argument expected. Usage : k [file.k]");

            // Code convention in the file "system.h" from UNIX
            System.exit(64);
        }

        if(args.length == 1){
            executeFile(args[0]);
        } else{
            executePrompt();
        }
    }

    private static void executeFile(String path) {
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.err.println("Error reading file " + path);
            System.exit(65);
        }

        execute(new String(bytes, Charset.defaultCharset()));
    }

    private static void executePrompt() {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.print(">>> ");
            String linea = null;

            try {
                linea = reader.readLine();
            } catch (IOException e) {
                System.err.println("Error reading from the console");
                continue;
            }

            if(linea == null) break; // Press Ctrl + D
            execute(linea);

            logger.reset();
        }
    }

    private static void execute(String source) {
        Interpreter interpreter = Interpreter.getInstance();
            Scanner scanner = new Scanner(source);
            if(logger.hasError()) return;

            Parser parser = new Parser(scanner);
            AST ast = parser.parse();
            if(logger.hasError()) return;

            Resolver resolver = new Resolver(interpreter);
            resolver.analyze(ast);
            if(logger.hasError()) return;

            Interpreter.getInstance().interpret(ast);
    }
}