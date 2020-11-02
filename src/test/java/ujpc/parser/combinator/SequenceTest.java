package ujpc.parser.combinator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static ujpc.parser.ParserTesting.canParse;
import static ujpc.parser.ParserTesting.cantParse;

import java.util.List;
import ujpc.parser.State;
import ujpc.parser.combinator.text.Token;

public class SequenceTest {
    public static class TokenSequenceTest {
        private final static Sequence<String, String> PARSER
            = new Sequence<String, String>(
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
        private final static Sequence<String, Integer> PARSER
            = new Sequence<String, Integer>(
                new Token("42").map(Integer::parseInt),
                new Token("23").map(Integer::parseInt));

        @Test void success() {
            canParse(PARSER, "4223", List.of(42, 23), "");
        }
    }
}
