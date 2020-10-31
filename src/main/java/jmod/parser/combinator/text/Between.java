package jmod.parser.combinator.text;

import jmod.parser.Parser;
import jmod.parser.State;
import jmod.parser.combinator.Sequence;
import static jmod.util.Lists.first;

public class Between<ResultT> implements Parser<String, ResultT> {
    private final String left;
    private final String right;
    private final Parser<String, ResultT> target;
    private final Parser<String, ResultT> parser;

    public Between(String left, String right, Parser<String, ResultT> target) {
        this.left = left;
        this.right = right;
        this.target = target;
        this.parser = new Sequence<String, ResultT>(
            new Token(left).map(x -> null),
            target,
            new Token(right).map(x -> null))
            .map(xs -> xs.get(0));
    }

    @Override
    public State<String, ResultT> parse(State.Success<String, ResultT> s) {
        return parser.parse(s);
    }
}
