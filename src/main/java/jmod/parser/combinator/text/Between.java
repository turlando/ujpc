package jmod.parser.combinator.text;

import jmod.parser.Parser;
import jmod.parser.State;
import jmod.parser.combinator.Sequence;
import static jmod.util.Lists.first;

public class Between<ResultT> implements Parser<String, ResultT> {
    private final Parser<String, String> leftParser;
    private final Parser<String, String> rightParser;
    private final Parser<String, ResultT> target;
    private final Parser<String, ResultT> parser;

    public Between(Parser<String, String> leftParser,
                   Parser<String, String> rightParser,
                   Parser<String, ResultT> target) {
        this.leftParser = leftParser;
        this.rightParser = rightParser;
        this.target = target;
        this.parser = new Sequence<String, ResultT>(
            leftParser.map(x -> null),
            target,
            rightParser.map(x -> null))
            .map(xs -> xs.get(0));
    }

    @Override
    public State<String, ResultT> parse(State.Success<String, ResultT> s) {
        return parser.parse(s);
    }
}
