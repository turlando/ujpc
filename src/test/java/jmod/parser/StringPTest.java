package jmod.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

class StringPTest {
    @Test void canParse() throws ParserException {
        StringP parser = new StringP("test");
        ParserState initial = new ParserState("test");
        ParserState result = parser.parse(initial);
        ParserState expected = new ParserState("test", List.of("test"), 4);
        assertTrue(result.equals(expected));
    }

    @Test void cantParse() throws ParserException {
        StringP parser = new StringP("test");
        ParserState initial = new ParserState("tset");
        assertThrows(ParserException.class, () -> parser.parse(initial));
    }
}
