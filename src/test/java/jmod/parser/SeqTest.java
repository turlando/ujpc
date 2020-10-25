package jmod.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

class SeqTest{
    @Test void canParse() {
        Seq parser = new Seq(new Str("hello"),
                             new Str(" "),
                             new Str("world"));
        State.Success initial = State.initial("hello world");
        State result = parser.parse(initial);
        State expected = new State.Success("hello world",
                                           11,
                                           List.of("hello", " ", "world"));
        assertTrue(result.equals(expected));
    }
}
