package ru.varfolomeev.parser;

import ru.varfolomeev.token.Brace;
import ru.varfolomeev.token.NumberToken;
import ru.varfolomeev.token.Operation;
import ru.varfolomeev.token.Token;

import java.util.List;

public class PrintVisitor implements TokenVisitor {
    @Override
    public void visit(NumberToken token) {
        System.out.print(token.getValue());
    }

    @Override
    public void visit(Brace token) {
        System.out.print(switch (token) {
            case LEFT -> '(';
            case RIGHT -> ')';
        });
    }

    @Override
    public void visit(Operation token) {
        System.out.print(switch (token) {
            case PLUS -> '+';
            case MINUS -> '-';
            case MUL -> '*';
            case DIV -> '/';
        });
    }

    public void print(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
            System.out.print(' ');
        }
        System.out.println();
    }

    public static void printTokens(List<Token> tokens) {
        new PrintVisitor().print(tokens);
    }
}
