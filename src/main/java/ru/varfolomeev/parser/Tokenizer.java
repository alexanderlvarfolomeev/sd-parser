package ru.varfolomeev.parser;

import lombok.RequiredArgsConstructor;
import ru.varfolomeev.token.Brace;
import ru.varfolomeev.token.NumberToken;
import ru.varfolomeev.token.Operation;
import ru.varfolomeev.token.Token;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class Tokenizer {
    private final static char EOF = (char) -1;
    private final static Map<Character, Token> char2token =
            Map.of(
                    '(', Brace.LEFT,
                    ')', Brace.RIGHT,
                    '+', Operation.PLUS,
                    '-', Operation.MINUS,
                    '*', Operation.MUL,
                    '/', Operation.DIV
            );

    public final InputStream inputStream;

    public final List<Token> tokens = new ArrayList<>();
    private char current;

    private boolean isEOF(char ch) {
        return ch == EOF || ch == '\n';
    }

    private char nextChar() {
        try {
            current = (char) inputStream.read();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return current;
    }

    private void skipWhiteSpaces() {
        while (Character.isWhitespace(current) && !isEOF(current)) {
            nextChar();
        }
    }

    public List<Token> tokenize() {
        nextChar();
        start();
        return tokens;
    }

    private void start() {
        skipWhiteSpaces();
        while (!isEOF(current)) {
            Token token = char2token.get(current);
            if (token == null) {
                if (Character.isDigit(current)) {
                    number();
                } else {
                    throw new RuntimeException("Unexpected character '" + current + "'");
                }
            } else {
                tokens.add(token);
                nextChar();
            }
            skipWhiteSpaces();
        }
    }

    private void number() {
        int value = current - '0';
        while (Character.isDigit(nextChar())) {
            value = 10 * value + (current - '0');
        }
        tokens.add(new NumberToken(value));
    }

    public static List<Token> tokenize(InputStream inputStream) {
        return new Tokenizer(inputStream).tokenize();
    }
}
