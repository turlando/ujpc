package ujpc.example.json;

import java.util.stream.Collectors;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.combinator.Sequence;
import ujpc.parser.combinator.Choice;
import ujpc.parser.combinator.Optional;
import ujpc.parser.combinator.text.Token;
import ujpc.parser.combinator.text.Regex;
import ujpc.parser.combinator.text.Between;
import ujpc.parser.combinator.text.Separated;
import ujpc.parser.combinator.text.WhitespaceConsumer;
import static ujpc.util.Lists.first;
import static ujpc.util.Maps.merge;

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
        public State<String, Json> parse(String in) {
            return new WhitespaceConsumer<Json>(parser).parse(in);
        }
    }

    public static class BooleanParser implements Parser<String, Json> {
        private Parser<String, Json> parser
            = new Choice<String, Json>(
                new Token("true").map(x -> new Json.Boolean(true)),
                new Token("false").map(x -> new Json.Boolean(false)));

        @Override
        public State<String, Json> parse(String in) {
            return new WhitespaceConsumer<Json>(parser).parse(in);
        }
    }

    public static class NumberParser implements Parser<String, Json> {
        private Parser<String, Json> parser
            = new Regex("(-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?)")
                  .map(x -> new Json.Number(Double.valueOf(first(x))));

        @Override
        public State<String, Json> parse(String in) {
            return new WhitespaceConsumer<Json>(parser).parse(in);
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
        public State<String, Json> parse(String in) {
            return new WhitespaceConsumer<Json>(parser).parse(in);
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
        public State<String, Json> parse(String in) {
            return parser.parse(in);
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
        public State<String, Json> parse(String in) {
            return parser.parse(in);
        }
    }

    @Override
    public State<String, Json> parse(String in) {
        return parser.parse(in);
    }
}
