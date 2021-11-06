package ru.varfolomeev.token;

import ru.varfolomeev.parser.TokenVisitor;

public enum Brace implements Token {
    LEFT, RIGHT;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
