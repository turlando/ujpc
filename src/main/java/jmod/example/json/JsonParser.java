package jmod.example.json;

import java.util.stream.Collectors;
import jmod.parser.Parser;
import jmod.parser.State;
import jmod.parser.combinator.Sequence;
import jmod.parser.combinator.Choice;
import jmod.parser.combinator.Optional;
import jmod.parser.combinator.text.Token;
import jmod.parser.combinator.text.Regex;
import jmod.parser.combinator.text.Between;
import jmod.parser.combinator.text.Separated;
import jmod.parser.combinator.text.WhitespaceConsumer;
import static jmod.util.Lists.first;
import static jmod.util.Maps.merge;

import java.util.List;
import java.util.Map;

public class JsonParser implements Parser<String, Json> {
    private Parser<String, Json> parser
        = new Choice<String, Json>(
            new NullParser(),
            new BooleanParser(),
            new NumberParser(),
            new StringParser(),
            new ArrayParser(),
            new ObjectParser());

    public static class NullParser implements Parser<String, Json> {
        private Parser<String, Json> parser
            = new Token("null").map(x -> new Json.Null());

        @Override
        public State<String, Json> parse(State.Success<String, Json> state) {
            return new WhitespaceConsumer<Json>(parser).parse(state);
        }
    }

    public static class BooleanParser implements Parser<String, Json> {
        private Parser<String, Json> parser
            = new Choice<String, Json>(
                new Token("true").map(x -> new Json.Boolean(true)),
                new Token("false").map(x -> new Json.Boolean(false)));

        @Override
        public State<String, Json> parse(State.Success<String, Json> state) {
            return new WhitespaceConsumer<Json>(parser).parse(state);
        }
    }

    public static class NumberParser implements Parser<String, Json> {
        private Parser<String, Json> parser
            = new Regex("(-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?)")
                  .map(x -> new Json.Number(Double.valueOf(first(x))));

        @Override
        public State<String, Json> parse(State.Success<String, Json> state) {
            return new WhitespaceConsumer<Json>(parser).parse(state);
        }
    }

    private static class StringParser implements Parser<String, Json> {
        private Parser<String, Json> parser
            = new Between<String>(
               new Token("\""),
               new Token("\""),
                new Optional<String, String>(
                    new Regex("([^\"]+)").map(x -> first(x)),
                    ""))
              .map(x -> new Json.String(x));

        @Override
        public State<String, Json> parse(State.Success<String, Json> state) {
            return new WhitespaceConsumer<Json>(parser).parse(state);
        }
    }

    private class ArrayParser implements Parser<String, Json> {
        private Parser<String, Json> parser
            = new Between<Json>(
                new WhitespaceConsumer<String>(new Token("[")),
                new WhitespaceConsumer<String>(new Token("]")),
                new Optional<String, Json>(
                    new Separated<Json>(
                        new WhitespaceConsumer<String>(new Token(",")),
                        new WhitespaceConsumer<Json>(JsonParser.this))
                        .map(x -> new Json.Array(x)),
                    new Json.Array()));

        @Override
        public State<String, Json> parse(State.Success<String, Json> state) {
            return parser.parse(state);
        }
    }

    private class ObjectParser implements Parser<String, Json> {
        private Parser<String, Json> parser
            = new Between<Json>(
                new WhitespaceConsumer<String>(new Token("{")),
                new WhitespaceConsumer<String>(new Token("}")),
                new Optional<String, Json>(
                    new Separated<Map<Json, Json>>(
                        new WhitespaceConsumer<String>(new Token(",")),
                        new Sequence<String, Json>(
                            new StringParser(),
                            new WhitespaceConsumer<String>(
                                new Token(":")).map(x -> null),
                            new WhitespaceConsumer<Json>(JsonParser.this))
                            .map(x -> Map.of(x.get(0), x.get(1))))
                    .map(x -> new Json.Object(merge(x))),
                    new Json.Object()));

        @Override
        public State<String, Json> parse(State.Success<String, Json> s) {
            return parser.parse(s);
        }
    }

    @Override
    public State<String, Json>
        parse(State.Success<String, Json> s) {
        return parser.parse(s);
    }
}
