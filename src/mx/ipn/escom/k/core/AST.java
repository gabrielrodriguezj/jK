package mx.ipn.escom.k.core;

import mx.ipn.escom.k.core.statement.Statement;

import java.util.List;

public record AST(List<Statement> statements) {
}
