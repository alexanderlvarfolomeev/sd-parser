package ru.varfolomeev.parser;

import ru.varfolomeev.token.*;

public interface TokenVisitor {
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
}
