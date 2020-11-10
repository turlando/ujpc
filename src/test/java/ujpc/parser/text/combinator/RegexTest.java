package ujpc.parser.text.combinator;

import java.util.List;
import org.junit.jupiter.api.Test;
import static ujpc.parser.text.TextParserTesting.canParse;
import static ujpc.parser.text.TextParserTesting.cantParse;

public class RegexTest {
    public static class StringRegexTest {
        private final static Regex PARSER = new Regex("([a-zA-Z]+)");

        @Test void success() {
            canParse(PARSER, "test42", List.of("test"), "42");
        }

        @Test void failure() {
            cantParse(PARSER, "42test");
        }
    }

    public static class IntegerRegexTest {
        private final static Regex PARSER = new Regex("([0-9]+)");

        @Test void success() {
            canParse(PARSER, "42test", List.of("42"), "test");
        }

        @Test void failure() {
            cantParse(PARSER, "test42");
        }
    }
}
