package ru.varfolomeev.token;

import ru.varfolomeev.parser.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
