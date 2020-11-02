package ujpc.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringsTest {
    @Test void take() {
        assertEquals("hello", Strings.take("hello world", 5));
        assertEquals("hello", Strings.take("hello", 5));
    }

    @Test void drop() {
        assertEquals(" world", Strings.drop("hello world", 5));
        assertEquals("", Strings.drop("hello", 5));
    }
}
