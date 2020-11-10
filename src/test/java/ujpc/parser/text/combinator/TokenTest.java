package ujpc.parser.text.combinator;

import org.junit.jupiter.api.Test;
import static ujpc.parser.text.TextParserTesting.canParse;
import static ujpc.parser.text.TextParserTesting.cantParse;

public class TokenTest {
    private final static Token PARSER = new Token("test");

    @Test void success() {
        canParse(PARSER, "test", "test", "");
    }

    @Test void failure() {
        cantParse(PARSER, "nope");
    }
}
