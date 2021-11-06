package ru.varfolomeev.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.varfolomeev.parser.TokenVisitor;

@AllArgsConstructor
public enum Operation implements Token {
    PLUS(0), MINUS(0), DIV(1), MUL(1);

    @Getter
    private final int priority;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
