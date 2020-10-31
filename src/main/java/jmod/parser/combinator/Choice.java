package jmod.parser.combinator;

import java.util.List;

import jmod.parser.Parser;
import jmod.parser.State;
import static jmod.util.Lists.first;
import static jmod.util.Lists.rest;

public class Choice<InputT, ResultT>
       implements Parser<InputT, ResultT> {
    private final List<Parser<InputT, ResultT>> parsers;

    private Choice(List<Parser<InputT, ResultT>> parsers) {
        this.parsers = parsers;
    }

    @SafeVarargs
    public Choice(Parser<InputT, ResultT>... parsers) {
        this.parsers = List.of(parsers);
    }

    @Override
    public State<InputT, ResultT> parse(State.Success<InputT, ResultT> s) {
        if (parsers.isEmpty())
            return new State.Failure<InputT, ResultT>(
                String.format("Could not match any of %s.",
                              parsers));

        return first(parsers).parse(s)
            .match(success -> success,
                   failure -> new Choice<InputT, ResultT>(rest(parsers))
                                  .parse(s));
    }
}
