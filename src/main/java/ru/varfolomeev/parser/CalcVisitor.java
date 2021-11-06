package ru.varfolomeev.parser;

import lombok.AllArgsConstructor;
import ru.varfolomeev.token.Brace;
import ru.varfolomeev.token.NumberToken;
import ru.varfolomeev.token.Operation;
import ru.varfolomeev.token.Token;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CalcVisitor implements TokenVisitor {
    private final List<Integer> stack = new ArrayList<>();

    private final List<Token> tokens;

    private void push(int value) {
        stack.add(value);
    }

    private int pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Odd operations.");
        } else {
            return stack.remove(stack.size() - 1);
        }
    }

    @Override
    public void visit(NumberToken token) {
        push(token.getValue());
    }

    @Override
    public void visit(Brace token) {
        throw new IllegalArgumentException("Braces in RPN.");
    }

    @Override
    public void visit(Operation token) {
        int arg2 = pop();
        int arg1 = pop();
        push(switch (token) {
            case PLUS -> arg1 + arg2;
            case MINUS -> arg1 - arg2;
            case MUL -> arg1 * arg2;
            case DIV -> arg1 / arg2;
        });
    }

    private int evaluate() {
        for (Token token : tokens) {
            token.accept(this);
        }
        if (stack.size() != 1) {
            throw new RuntimeException("Operand count mismatch.");
        } else {
            return stack.get(0);
        }
    }

    public static int evaluate(List<Token> tokens) {
        return new CalcVisitor(tokens).evaluate();
    }
}
