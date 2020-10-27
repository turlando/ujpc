package jmod.parser.string;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jmod.parser.State;

class StrTest {
    @Test void parseWithSuccess() {
        Str<String> parser = new Str<>("test");
        State.Success<StringInput, String> initial
            = new State.Success<>(new StringInput("test"), "");
        State<StringInput, String> result
            = parser.parse(initial, (s, r) -> r.get());
        State.Success<StringInput, String> expected
            = new State.Success<>(new StringInput(""), "test");
        assertEquals(expected, result);
    }

    @Test void parseWithFailure() {
        Str<String> parser = new Str<>("test");
        State.Success<StringInput, String> initial
            = new State.Success<>(new StringInput("nope"), "");
        State<StringInput, String> result
            = parser.parse(initial, (s, r) -> r.get());
        assertEquals(result.getClass(), State.Failure.class);
    }

    @Test void parseWithTransformation() {
        Str<String> parser = new Str<>("test");
        State.Success<StringInput, String> initial
            = new State.Success<>(new StringInput("test"), "");
        State<StringInput, String> result
            = parser.parse(initial, (s, r) -> r.get().toUpperCase());
        State.Success<StringInput, String> expected
            = new State.Success<>(new StringInput(""), "TEST");
        assertEquals(expected, result);
    }
}
