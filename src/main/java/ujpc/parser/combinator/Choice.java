package ujpc.parser.combinator;

import java.util.List;

import ujpc.parser.Parser;
import ujpc.parser.State;
import static ujpc.util.Lists.first;
import static ujpc.util.Lists.rest;
import static ujpc.util.Lists.append;

public class Choice<InputT, ResultT>
       implements Parser<InputT, ResultT> {
    private final List<Parser<InputT, ResultT>> parsers;
    private final List<Parser<InputT, ResultT>> failedParsers;

    private Choice(List<Parser<InputT, ResultT>> parsers,
                   List<Parser<InputT, ResultT>> failedParsers) {
        this.parsers = parsers;
        this.failedParsers = failedParsers;
    }

    private Choice(List<Parser<InputT, ResultT>> parsers) {
        this(parsers, List.of());
    }

    @SafeVarargs
    public Choice(Parser<InputT, ResultT>... parsers) {
        this(List.of(parsers), List.of());
    }

    @Override
    public State<InputT, ResultT> parse(State.Success<InputT, ResultT> s) {
        if (parsers.isEmpty())
            return new State.Failure<InputT, ResultT>(
                String.format("Could not match any of %s.", failedParsers));

        return first(parsers).parse(s)
            .match(success -> success,
                   failure -> new Choice<InputT, ResultT>(
                                  rest(parsers),
                                  append(failedParsers, first(parsers)))
                              .parse(s));
    }
}
