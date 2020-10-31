package jmod.parser.combinator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import jmod.parser.State;
import jmod.parser.combinator.text.Token;

class SequenceTest {
    @Test void parseWithSuccess() {
        Sequence<String, String> parser
            = new Sequence<String, String>(
                new Token("hello"),
                new Token(" "),
                new Token("world"));
        State.Success<String, List<String>> initial
            = new State.Success<>("hello world", null);
        State<String, List<String>> result = parser.parse(initial);
        State.Success<String, List<String>> expected
            = new State.Success<>("", List.of("hello", " ", "world"));
        assertEquals(expected, result);
    }

    @Test void parseWithFailure() {
        Sequence<String, String> parser
            = new Sequence<String, String>(
                new Token("hello"),
                new Token(" "),
                new Token("there"));
        State.Success<String, List<String>> initial
            = new State.Success<>("hello world", null);
        State<String, List<String>> result = parser.parse(initial);
        assertEquals(result.getClass(), State.Failure.class);
    }

    @Test void parseWithTransformation() {
        Sequence<String, Integer> parser
            = new Sequence<String, Integer>(
                new Token("42").map(Integer::parseInt),
                new Token("23").map(Integer::parseInt));
        State.Success<String, List<Integer>> initial
            = new State.Success<>("4223", null);
        State.Success<String, List<Integer>> expected
            = new State.Success<>("", List.of(42, 23));
        State<String, List<Integer>> result = parser.parse(initial);
        assertEquals(expected, result);
    }
}
