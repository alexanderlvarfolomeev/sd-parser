package ru.varfolomeev.parser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.varfolomeev.token.Brace;
import ru.varfolomeev.token.NumberToken;
import ru.varfolomeev.token.Operation;
import ru.varfolomeev.token.Token;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ParserVisitor implements TokenVisitor {
    private final List<Token> infixTokens;

    private final ArrayList<Token> rpnStack = new ArrayList<>();

    @Getter
    private final List<Token> rpnTokens = new ArrayList<>();

    public List<Token> process() {
        for (Token token : infixTokens) {
            token.accept(this);
        }
        Token token;
        while (!rpnStack.isEmpty()) {
            token = pop();
            if (token instanceof Brace) {
                throw new RuntimeException("Mismatched brackets.");
            }
            rpnTokens.add(token);
        }
        return getRpnTokens();
    }

    private void push(Token token) {
        rpnStack.add(token);
    }

    private Token top() {
        return rpnStack.isEmpty() ? null : rpnStack.get(rpnStack.size() - 1);
    }

    private Token pop() {
        if (rpnStack.isEmpty()) {
            throw new RuntimeException("Mismatched brackets.");
        } else {
            return rpnStack.remove(rpnStack.size() - 1);
        }
    }

    private boolean isGEqPriorityOperation(Operation operation, Token token) {
        return token instanceof Operation && operation.getPriority() <= ((Operation) token).getPriority();
    }

    @Override
    public void visit(NumberToken numberToken) {
        rpnTokens.add(numberToken);
    }

    @Override
    public void visit(Brace brace) {
        switch (brace) {
            case LEFT -> push(brace);
            case RIGHT -> {
                Token token;
                while ((token = pop()) != Brace.LEFT) {
                    rpnTokens.add(token);
                }
            }
        }
    }

    @Override
    public void visit(Operation operation) {
        Token token;
        while (isGEqPriorityOperation(operation, token = top())) {
            pop();
            rpnTokens.add(token);
        }
        push(operation);
    }

    public static List<Token> toRPN(List<Token> infixTokens) {
        return new ParserVisitor(infixTokens).process();
    }
}
