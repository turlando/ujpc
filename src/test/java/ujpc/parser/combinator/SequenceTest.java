package ujpc.parser.combinator;

import org.junit.jupiter.api.Test;
import static ujpc.parser.text.TextParserTesting.canParse;
import static ujpc.parser.text.TextParserTesting.cantParse;
import java.util.List;
import ujpc.parser.text.Text;
import ujpc.parser.text.combinator.Token;

public class SequenceTest {
    public static class TokenSequenceTest {
        private final static Sequence<Text, String> PARSER
            = new Sequence<>(
                new Token("hello"),
                new Token(" "),
                new Token("world"));

        @Test void success() {
            canParse(PARSER, "hello world", List.of("hello", " ", "world"), "");
        }

        @Test void failure() {
            cantParse(PARSER, "hello there");
        }
    }

    public static class IntegerSequenceTest {
        private final static Sequence<Text, Integer> PARSER
            = new Sequence<>(
                new Token("42").map(Integer::parseInt),
                new Token("23").map(Integer::parseInt));

        @Test void success() {
            canParse(PARSER, "4223", List.of(42, 23), "");
        }
    }
}
