package ujpc.parser.combinator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ujpc.parser.State;
import ujpc.parser.combinator.text.Token;

public class ChoiceTest {
    @Test void parseFirst() {
        Choice<String, String> parser
            = new Choice<>(
                new Token("foo"),
                new Token("bar"),
                new Token("baz"));
        State.Success<String, String> initial
            = new State.Success<>("foo test", "");
        State<String, String> result = parser.parse(initial);
        State.Success<String, String> expected
            = new State.Success<>(" test", "foo");
        assertEquals(expected, result);
    }

    @Test void parseSecond() {
        Choice<String, String> parser
            = new Choice<>(
                new Token("foo"),
                new Token("bar"),
                new Token("baz"));
        State.Success<String, String> initial
            = new State.Success<>("bar test", "");
        State<String, String> result = parser.parse(initial);
        State.Success<String, String> expected
            = new State.Success<>(" test", "bar");
        assertEquals(expected, result);
    }

    @Test void parseThird() {
        Choice<String, String> parser
            = new Choice<>(
                new Token("foo"),
                new Token("bar"),
                new Token("baz"));
        State.Success<String, String> initial
            = new State.Success<>("baz test", "");
        State<String, String> result = parser.parse(initial);
        State.Success<String, String> expected
            = new State.Success<>(" test", "baz");
        assertEquals(expected, result);
    }

    @Test void parseWithFailure() {
        Choice<String, String> parser
            = new Choice<>(
                new Token("foo"),
                new Token("bar"),
                new Token("baz"));
        State.Success<String, String> initial
            = new State.Success<>("quux test", "");
        State<String, String> result = parser.parse(initial);
        assertEquals(State.Failure.class, result.getClass());
    }
}
