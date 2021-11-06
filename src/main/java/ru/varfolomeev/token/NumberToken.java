package ru.varfolomeev.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.varfolomeev.parser.TokenVisitor;

@Getter
@AllArgsConstructor
public class NumberToken implements Token {
    private final int value;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
