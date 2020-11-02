package ujpc.parser.combinator.text;

import org.junit.jupiter.api.Test;

import static ujpc.parser.ParserTesting.canParse;
import static ujpc.parser.ParserTesting.cantParse;

public class TokenTest {
    private final static Token PARSER = new Token("test");

    @Test void success() {
        canParse(PARSER, "test", "test", "");
    }

    @Test void failure() {
        cantParse(PARSER, "nope");
    }
}
