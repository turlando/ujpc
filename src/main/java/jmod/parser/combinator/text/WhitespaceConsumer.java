package jmod.parser.combinator.text;

import jmod.parser.Parser;
import jmod.parser.State;
import jmod.parser.combinator.Sequence;

import static jmod.util.Lists.first;

public class WhitespaceConsumer<ResultT> implements Parser<String, ResultT> {
    private Parser<String, ResultT> parser;

    public WhitespaceConsumer(Parser<String, ResultT> parser) {
        this.parser = parser;
    }

    @Override
    public State<String, ResultT> parse(State.Success<String, ResultT> s) {
        return new Sequence<String, ResultT>(
            new Whitespace().map(x -> null),
            parser)
        .map(x -> first(x))
        .parse(s);
    }
}
