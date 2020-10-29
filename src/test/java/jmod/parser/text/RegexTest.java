package jmod.parser.text;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import jmod.parser.State;

class RegexTest {
    @Test void parseString() {
        Regex parser = new Regex("([a-zA-Z]+)");
        State.Success<String, List<String>> initial
            = new State.Success<>("test42", null);
        State<String, List<String>> result = parser.parse(initial);
        State.Success<String, List<String>> expected
            = new State.Success<>("42", List.of("test"));
        assertEquals(expected, result);
    }

    @Test void parseInt() {
        Regex parser = new Regex("([0-9]+)");
        State.Success<String, List<String>> initial
            = new State.Success<>("42test", null);
        State<String, List<String>> result = parser.parse(initial);
        State.Success<String, List<String>> expected
            = new State.Success<>("test", List.of("42"));
        assertEquals(expected, result);
    }

    @Test void parseWithFailure() {
        Regex parser = new Regex("test");
        State.Success<String, List<String>> initial
            = new State.Success<>("nopetest", List.of());
        State<String, List<String>> result = parser.parse(initial);
        assertEquals(result.getClass(), State.Failure.class);
    }
}
