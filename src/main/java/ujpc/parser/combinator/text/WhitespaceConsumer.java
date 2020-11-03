package ujpc.parser.combinator.text;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.combinator.Sequence;

import static ujpc.util.Lists.first;

public class WhitespaceConsumer<ResultT> implements Parser<String, ResultT> {
    private Parser<String, ResultT> parser;

    public WhitespaceConsumer(Parser<String, ResultT> parser) {
        this.parser = parser;
    }

    @Override
    public State<String, ResultT> parse(String in) {
        return new Sequence<String, ResultT>(
            new Whitespace().map(x -> null),
            parser)
        .map(x -> first(x))
        .parse(in);
    }
}
