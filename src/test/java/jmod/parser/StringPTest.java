package jmod.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringPTest {
    @Test void canParse() throws ParserException {
        StringP parser = new StringP("test");
        ParserState result = parser.parse("test", 0);
        ParserState expected = new ParserState("test", 4);
        assertTrue(result.equals(expected));
    }

    @Test void cantParse() throws ParserException {
        StringP parser = new StringP("test");
        assertThrows(ParserException.class, () -> parser.parse("tset", 0));
    }
}
