package jmod.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

class SequencePTest {
    @Test void canParse() throws ParserException {
        SequenceP parser = new SequenceP(List.of(new StringP("hello"),
                                                 new StringP(" "),
                                                 new StringP("world")));
        ParserState initial = new ParserState("hello world");
        ParserState result = parser.parse(initial);
        ParserState expected = new ParserState("hello world",
                                               List.of("hello", " ", "world"),
                                               11);
        assertTrue(result.equals(expected));
    }
}
