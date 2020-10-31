package jmod.parser.combinator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jmod.parser.State;
import jmod.parser.combinator.text.Token;

public class ChoiceTest {
    @Test void parseFirst() {
        Choice<String, String> parser
            = new Choice<>(
                new Token("foo"),
                new Token("bar"),
                new Token("baz"));
        State.Success<String, String> initial
            = new State.Success<>("foo test", null);
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
            = new State.Success<>("bar test", null);
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
            = new State.Success<>("baz test", null);
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
            = new State.Success<>("quux test", null);
        State<String, String> result = parser.parse(initial);
        assertEquals(State.Failure.class, result.getClass());
    }
}
