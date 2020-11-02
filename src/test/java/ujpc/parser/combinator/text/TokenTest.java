package ujpc.parser.combinator.text;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static ujpc.parser.ParserTesting.canParse;
import static ujpc.parser.ParserTesting.cantParse;

import ujpc.parser.Parser;
import ujpc.parser.State;

class TokenTest {
    private final static Token PARSER = new Token("test");

    @Test void success() {
        canParse(PARSER, "test", "test", "");
    }

    @Test void failure() {
        cantParse(PARSER, "nope");
    }
}
