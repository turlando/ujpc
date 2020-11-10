package ujpc.parser.combinator;

import org.junit.jupiter.api.Test;
import static ujpc.parser.text.TextParserTesting.canParse;
import static ujpc.parser.text.TextParserTesting.cantParse;
import ujpc.parser.text.Text;
import ujpc.parser.text.combinator.Token;

public class ChoiceTest {
    public static class TokenChoiceTest {
        private final static Choice<Text, String> PARSER
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
