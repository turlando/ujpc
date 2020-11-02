package ujpc.example.json;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static ujpc.parser.ParserTesting.canParse;
import static ujpc.parser.ParserTesting.cantParse;

import java.util.List;
import java.util.Map;

class JsonParserTest {
    private final static JsonParser PARSER = new JsonParser();

    @Nested
    public class NullTest {
        private final Json.Null n = new Json.Null();

        @Test void simple() {
            canParse(PARSER, "null", n, "");
        }

        @Test void spaces() {
            canParse(PARSER, "  null ", n, " ");
        }
    }

    @Nested
    public class BooleanTest {
        private final Json.Boolean t = new Json.Boolean(true);
        private final Json.Boolean f = new Json.Boolean(false);

        @Test void simpleTrue() {
            canParse(PARSER, "true", t, "");
        }

        @Test void spacesTrue() {
            canParse(PARSER, "  true ", t, " ");
        }

        @Test void simpleFalse() {
            canParse(PARSER, "false", f, "");
        }

        @Test void spacesFalse() {
            canParse(PARSER, "  false ", f, " ");
        }
    }

    @Nested
    public class NumberTest {
        @Test void simple() {
            canParse(PARSER, "-42.23e7", new Json.Number(-42.23e7), "");
        }

        @Test void spaces() {
            canParse(PARSER, "  -42.23e7 ", new Json.Number(-42.23e7), " ");
        }
    }

    @Nested
    public class StringTest {
        @Test void emptyString() {
            canParse(PARSER, "\"\"", new Json.String(""), "");
        }

        @Test void simple() {
            canParse(PARSER, "\"foo bar baz\"",
                     new Json.String("foo bar baz"), "");
        }

        @Test void spacesString() {
            canParse(PARSER, "  \"foo bar baz\" ",
                     new Json.String("foo bar baz"), " ");
        }

        @Test void numericString() {
            canParse(PARSER, "\"42\"", new Json.String("42"), "");
        }
    }

    @Nested
    public class ArrayTest {
        @Test void empty() {
            canParse(PARSER, "[]", new Json.Array(List.of()), "");
        }

        @Test void singleNull() {
            canParse(PARSER, "[null]",
                     new Json.Array(new Json.Null()), "");
        }

        @Test void singleString() {
            canParse(PARSER, "[\"foo\"]",
                     new Json.Array(new Json.String("foo")), "");
        }

        @Test void strings() {
            canParse(PARSER, "[\"foo\",\"bar\"]",
                     new Json.Array(new Json.String("foo"),
                                    new Json.String("bar")),
                     "");
        }

        @Test void nested() {
            canParse(PARSER, "[\"foo\",42,[\"foo\",42,[\"foo\",42]]]",
                     new Json.Array(
                        new Json.String("foo"),
                        new Json.Number(42),
                        new Json.Array(
                            new Json.String("foo"),
                            new Json.Number(42),
                            new Json.Array(
                                new Json.String("foo"),
                                new Json.Number(42)))),
                     "");
        }

        @Test void spaces() {
            canParse(PARSER,
                     "  [  \"foo\"  ,  \"bar\"  ] ",
                     new Json.Array(new Json.String("foo"),
                                    new Json.String("bar")),
                     " ");
        }
    }

    @Nested
    public class ObjectTest {
        @Test void empty() {
            canParse(PARSER, "{}", new Json.Object(), "");
        }

        @Test void simple() {
            canParse(PARSER, "{\"key1\": \"val1\"}",
                     new Json.Object(Map.of(
                         new Json.String("key1"), new Json.String("val1"))),
                     "");
        }

        @Test void heterogeneous() {
            canParse(PARSER,
                     "{\"key1\": \"val1\", \"key2\": 42, \"key3\": [23, \"42\"]}",
                     new Json.Object(Map.of(
                         new Json.String("key1"), new Json.String("val1"),
                         new Json.String("key2"), new Json.Number(42),
                         new Json.String("key3"), new Json.Array(
                             new Json.Number(23),
                             new Json.String("42")))),
                     "");
        }
    }
}
