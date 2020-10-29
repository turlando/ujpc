package jmod.parser.text;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jmod.parser.State;

class TokenTest {
    @Test void parseWithSuccess() {
        Token parser = new Token("test");
        State.Success<String, String> initial
            = new State.Success<>("test", "");
        State<String, String> result = parser.parse(initial);
        State.Success<String, String> expected
            = new State.Success<>("", "test");
        assertEquals(expected, result);
    }

    @Test void parseWithFailure() {
        Token parser = new Token("test");
        State.Success<String, String> initial
            = new State.Success<>("nope", "");
        State<String, String> result = parser.parse(initial);
        assertEquals(result.getClass(), State.Failure.class);
    }

    // @Test void parseWithTransformation() {
    //     Str<String> parser = new Str<>("test");
    //     State.Success<StringInput, String> initial
    //         = new State.Success<>(new StringInput("test"), "");
    //     State<StringInput, String> result
    //         = parser.parse(initial, (s, r) -> r.get().toUpperCase());
    //     State.Success<StringInput, String> expected
    //         = new State.Success<>(new StringInput(""), "TEST");
    //     assertEquals(expected, result);
    // }
}
