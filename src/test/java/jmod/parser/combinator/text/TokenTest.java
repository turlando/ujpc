package jmod.parser.combinator.text;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jmod.parser.Parser;
import jmod.parser.State;

class TokenTest {
    @Test void parseWithSuccess() {
        Token parser = new Token("test");
        State.Success<String, String> initial
            = new State.Success<>("test", "");
        State.Success<String, String> expected
            = new State.Success<>("", "test");
        State<String, String> result = parser.parse(initial);
        assertEquals(expected, result);
    }

    @Test void parseWithFailure() {
        Token parser = new Token("test");
        State.Success<String, String> initial
            = new State.Success<>("nope", "");
        State<String, String> result = parser.parse(initial);
        assertEquals(result.getClass(), State.Failure.class);
    }

    @Test void parseWithTransformation() {
        Parser<String, Integer> parser
            = new Token("42").map(Integer::parseInt);
        State.Success<String, Integer> initial
            = new State.Success<>("42", 0);
        State.Success<String, Integer> expected
            = new State.Success<>("", 42);
        State<String, Integer> result = parser.parse(initial);
        assertEquals(expected, result);
    }
}
