package ujpc.parser.combinator.text;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import ujpc.parser.State;
import ujpc.parser.combinator.Choice;

class TokenChoiceTest {
    @Test void parseFirst() {
        Choice<String, String> parser
            = new Choice<String, String>(
                new Token("this"),
                new Token("that"));
        State.Success<String, String> initial
            = new State.Success<>("this that", "");
        State<String, String> result = parser.parse(initial);
        State.Success<String, String> expected
            = new State.Success<>(" that", "this");
        assertEquals(expected, result);
    }

    @Test void parseSecond() {
        Choice<String, String> parser
            = new Choice<String, String>(
                new Token("this"),
                new Token("that"));
            State.Success<String, String> initial
                = new State.Success<>("that this", "");
            State<String, String> result = parser.parse(initial);
            State.Success<String, String> expected
                = new State.Success<>(" this", "that");
            assertEquals(expected, result);
        }

    @Test void parseWithFailure() {
        Choice<String, String> parser
            = new Choice<String, String>(
                new Token("this"),
                new Token("that"));
        State.Success<String, String> initial
            = new State.Success<>("nope", "");
        State<String, String> result = parser.parse(initial);
        assertEquals(result.getClass(), State.Failure.class);
    }
}
