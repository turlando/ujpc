package jmod.parser.text;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import jmod.parser.State;
import jmod.parser.Sequence;

class TokenSequenceTest {
    @Test void parseWithSuccess() {
        Sequence<String, String> parser
            = new Sequence<String, String>(
                new Token("hello"),
                new Token(" "),
                new Token("world"));
        State.Success<String, List<String>> initial
            = new State.Success<>("hello world", List.of());
        State<String, List<String>> result = parser.parse(initial);
        State.Success<String, List<String>> expected
            = new State.Success<>("", List.of("hello", " ", "world"));
        assertEquals(expected, result);
    }
}
