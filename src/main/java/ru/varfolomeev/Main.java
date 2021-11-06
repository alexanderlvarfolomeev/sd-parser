package ru.varfolomeev;

import ru.varfolomeev.parser.CalcVisitor;
import ru.varfolomeev.parser.ParserVisitor;
import ru.varfolomeev.parser.PrintVisitor;
import ru.varfolomeev.parser.Tokenizer;

public class Main {
    public static void main(String... args) {
        var tokens = ParserVisitor.toRPN(Tokenizer.tokenize(System.in));

        PrintVisitor.printTokens(tokens);
        System.out.println(CalcVisitor.evaluate(tokens));
    }
}
