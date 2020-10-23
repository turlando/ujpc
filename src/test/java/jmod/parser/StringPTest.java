package jmod.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringPTest {
    @Test void canParse() throws ParserException {
        StringP parser = new StringP("test");
        assertEquals("test", parser.parse("test", 0));
    }

    @Test void cantParse() throws ParserException {
        StringP parser = new StringP("test");
        assertThrows(ParserException.class, () -> parser.parse("tset", 0));
    }
}
