package ujpc.parser.combinator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static ujpc.parser.ParserTesting.canParse;
import static ujpc.parser.ParserTesting.cantParse;

import ujpc.parser.State;
import ujpc.parser.combinator.text.Token;

public class ChoiceTest {
    public static class TokenChoiceTest {
        private final static Choice<String, String> PARSER
            = new Choice<>(
                new Token("foo"),
                new Token("bar"),
                new Token("baz"));

        @Test void parseFirst() {
            canParse(PARSER, "foo test", "foo", " test");
        }

        @Test void parseSecond() {
            canParse(PARSER, "bar test", "bar", " test");
        }

        @Test void parseThird() {
            canParse(PARSER, "baz test", "baz", " test");
        }

        @Test void failure() {
            cantParse(PARSER, "quux test");
        }
    }
}
