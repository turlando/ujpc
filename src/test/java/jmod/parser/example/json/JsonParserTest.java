package jmod.parser.example.json;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jmod.parser.State;

import java.util.List;

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

    @Test void parseTrue() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("true", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("", new Json.Boolean(true));
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

    @Test void parseNumber() {
        JsonParser parser = new JsonParser();
        State.Success<String, Json> initial
            = new State.Success<>("-42.23e7", new Json.Object());
        State<String, Json> result = parser.parse(initial);
        State.Success<String, Json> expected
            = new State.Success<>("", new Json.Number(-42.23e7));
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
}
