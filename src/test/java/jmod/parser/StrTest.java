package jmod.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

class StrTest {
    @Test void canParse() {
        Str parser = new Str("test");
        State.Success initial = State.initial("test");
        State result = parser.parse(initial);
        State expected = new State.Success("test", 4, List.of("test"));
        assertTrue(result.equals(expected));
    }

    @Test void cantParse() {
        Str parser = new Str("test");
        State.Success initial = State.initial("test");
        State result = parser.parse(initial);
        assertTrue(result instanceof State.Success);
    }
}
