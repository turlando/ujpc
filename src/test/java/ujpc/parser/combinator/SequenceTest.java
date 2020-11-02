package ujpc.parser.combinator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import ujpc.parser.State;
import ujpc.parser.combinator.text.Token;

public class SequenceTest {
    public static class ParseTokens {
        private final static Sequence<String, String> PARSER
            = new Sequence<String, String>(
                new Token("hello"),
                new Token(" "),
                new Token("world"));

        @Test void success() {
            State.Success<String, List<String>> expected
                = new State.Success<>("", List.of("hello", " ", "world"));
            assertEquals(expected, PARSER.parse("hello world"));
        }

        @Test void failure() {
            assertEquals(State.Failure.class,
                         PARSER.parse("hello there").getClass());
        }
    }

    public static class ParseIntegers {
        private final static Sequence<String, Integer> PARSER
            = new Sequence<String, Integer>(
                new Token("42").map(Integer::parseInt),
                new Token("23").map(Integer::parseInt));

        @Test void success() {
            State.Success<String, List<Integer>> expected
                = new State.Success<>("", List.of(42, 23));
            assertEquals(expected, PARSER.parse("4223"));
        }
    }
}
