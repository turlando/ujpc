package jmod.example.json;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jmod.parser.State;

import java.util.List;
import java.util.Map;

class JsonParserTest {
    @Test void parseNull() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("null", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("", new Json.Null());
        assertEquals(expected, result);
    }

    @Test void parseNullWithSpaces() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("  null ", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(" ", new Json.Null());
        assertEquals(expected, result);
    }

    @Test void parseTrue() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("true", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("", new Json.Boolean(true));
        assertEquals(expected, result);
    }

    @Test void parseTrueWithSpaces() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("  true ", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(" ", new Json.Boolean(true));
        assertEquals(expected, result);
    }

    @Test void parseFalse() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("false", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("", new Json.Boolean(false));
        assertEquals(expected, result);
    }

    @Test void parseFalseWithSpaces() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("false", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("", new Json.Boolean(false));
        assertEquals(expected, result);
    }

    @Test void parseNumber() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("-42.23e7", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("", new Json.Number(-42.23e7));
        assertEquals(expected, result);
    }

    @Test void parseNumberWithSpaces() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("  -42.23e7 ", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(" ", new Json.Number(-42.23e7));
        assertEquals(expected, result);
    }

    @Test void parseEmptyString() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("\"\"", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                 "", new Json.String(""));
        assertEquals(expected, result);
    }

    @Test void parseString() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("\"foo bar baz\"", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                "", new Json.String("foo bar baz"));
        assertEquals(expected, result);
    }

    @Test void parseStringWithSpaces() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("  \"foo bar baz\" ", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                " ", new Json.String("foo bar baz"));
        assertEquals(expected, result);
    }

    @Test void parseNumericString() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("\"42\"", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                "", new Json.String("42"));
        assertEquals(expected, result);
    }

    @Test void parseEmptyArray() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("[]", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                "", new Json.Array(List.of()));
        assertEquals(expected, result);
    }

    @Test void parseArrayOfNull() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("[null]", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                "", new Json.Array(new Json.Null()));
        assertEquals(expected, result);
    }

    @Test void parseArrayOfString() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("[\"foo\"]", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                "", new Json.Array(new Json.String("foo")));
        assertEquals(expected, result);
    }

    @Test void parseArrayOfStrings() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("[\"foo\",\"bar\"]", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                "", new Json.Array(new Json.String("foo"),
                                   new Json.String("bar")));
        assertEquals(expected, result);
    }

    @Test void parseNestedArray() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("[\"foo\",42,[\"foo\",42,[\"foo\",42]]]",
                                  new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                "",
                new Json.Array(
                    new Json.String("foo"),
                    new Json.Number(42),
                    new Json.Array(
                        new Json.String("foo"),
                        new Json.Number(42),
                        new Json.Array(
                            new Json.String("foo"),
                            new Json.Number(42)))));
        assertEquals(expected, result);
    }

    @Test void parseArrayWithSpaces() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>(
                "  [  \"foo\"  ,  \"bar\"  ] ",
                new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>(
                " ", new Json.Array(new Json.String("foo"),
                                    new Json.String("bar")));
        assertEquals(expected, result);
    }

    @Test void parseEmptyObject() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("{}", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("", new Json.Object());
        assertEquals(expected, result);
    }

    @Test void parseSimpleObject() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>(
                "{\"key1\": \"val1\"}",
                new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("",
                 new Json.Object(Map.of(
                     new Json.String("key1"), new Json.String("val1"))));
        assertEquals(expected, result);
    }

    @Test void parseObject() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>(
                "{\"key1\": \"val1\", \"key2\": 42, \"key3\": [23, \"42\"]}",
                new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("",
                new Json.Object(Map.of(
                    new Json.String("key1"), new Json.String("val1"),
                    new Json.String("key2"), new Json.Number(42),
                    new Json.String("key3"), new Json.Array(
                        new Json.Number(23),
                        new Json.String("42")))));
        assertEquals(expected, result);
    }
}
