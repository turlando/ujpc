package ujpc.example.json;

import java.util.stream.Collectors;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;
import ujpc.parser.combinator.Sequence;
import ujpc.parser.combinator.Choice;
import ujpc.parser.combinator.Optional;
import ujpc.parser.text.combinator.Token;
import ujpc.parser.text.combinator.Regex;
import ujpc.parser.text.combinator.Between;
import ujpc.parser.text.combinator.Separated;
import ujpc.parser.text.combinator.WhitespaceConsumer;
import static ujpc.util.Lists.first;
import static ujpc.util.Maps.merge;

import java.util.List;
import java.util.Map;

public class JsonParser implements Parser<Text, Json> {
    private Parser<Text, Json> parser
        = new Choice<Text, Json>(
            new NullParser(),
            new BooleanParser(),
            new NumberParser(),
            new StringParser(),
            new ArrayParser(),
            new ObjectParser());

    public static class NullParser implements Parser<Text, Json> {
        private Parser<Text, Json> parser
            = new Token("null").map(x -> new Json.Null());

        @Override
        public State<Text, Json> parse(Text in) {
            return new WhitespaceConsumer<Json>(parser).parse(in);
        }

        public String toString() { return "JsonParser.NullParser()"; }
    }

    public static class BooleanParser implements Parser<Text, Json> {
        private Parser<Text, Json> parser
            = new Choice<Text, Json>(
                new Token("true").map(x -> new Json.Boolean(true)),
                new Token("false").map(x -> new Json.Boolean(false)));

        @Override
        public State<Text, Json> parse(Text in) {
            return new WhitespaceConsumer<Json>(parser).parse(in);
        }

        public String toString() { return "JsonParser.BooleanParser()"; }
    }

    public static class NumberParser implements Parser<Text, Json> {
        private Parser<Text, Json> parser
            = new Regex("(-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?)")
                  .map(x -> new Json.Number(Double.valueOf(first(x))));

        @Override
        public State<Text, Json> parse(Text in) {
            return new WhitespaceConsumer<Json>(parser).parse(in);
        }

        public String toString() { return "JsonParser.NumberParser()"; }
    }

    private static class StringParser implements Parser<Text, Json> {
        private Parser<Text, Json> parser
            = new Between<String>(
               new Token("\""),
               new Token("\""),
                new Optional<Text, String>(
                    new Regex("([^\"]+)").map(x -> first(x)),
                    ""))
              .map(x -> new Json.String(x));

        @Override
        public State<Text, Json> parse(Text in) {
            return new WhitespaceConsumer<Json>(parser).parse(in);
        }

        public String toString() { return "JsonParser.StringParser()"; }
    }

    private class ArrayParser implements Parser<Text, Json> {
        private Parser<Text, Json> parser
            = new Between<Json>(
                new WhitespaceConsumer<String>(new Token("[")),
                new WhitespaceConsumer<String>(new Token("]")),
                new Optional<Text, Json>(
                    new Separated<Json>(
                        new WhitespaceConsumer<String>(new Token(",")),
                        new WhitespaceConsumer<Json>(JsonParser.this))
                        .map(x -> new Json.Array(x)),
                    new Json.Array()));

        @Override
        public State<Text, Json> parse(Text in) {
            return parser.parse(in);
        }

        public String toString() { return "JsonParser.ArrayParser()"; }
    }

    private class ObjectParser implements Parser<Text, Json> {
        private Parser<Text, Json> parser
            = new Between<Json>(
                new WhitespaceConsumer<String>(new Token("{")),
                new WhitespaceConsumer<String>(new Token("}")),
                new Optional<Text, Json>(
                    new Separated<Map<Json, Json>>(
                        new WhitespaceConsumer<String>(new Token(",")),
                        new Sequence<Text, Json>(
                            new StringParser(),
                            new WhitespaceConsumer<String>(
                                new Token(":")).map(x -> null),
                            new WhitespaceConsumer<Json>(JsonParser.this))
                            .map(x -> Map.of(x.get(0), x.get(1))))
                    .map(x -> new Json.Object(merge(x))),
                    new Json.Object()));

        @Override
        public State<Text, Json> parse(Text in) {
            return parser.parse(in);
        }

        public String toString() { return "JsonParser.ObjectParser()"; }
    }

    @Override
    public State<Text, Json> parse(Text in) {
        return parser.parse(in);
    }
}
