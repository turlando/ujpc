package ujpc.parser.combinator;

import java.util.List;

import ujpc.parser.Parser;
import ujpc.parser.State;
import static ujpc.util.Lists.append;
import static ujpc.util.Lists.first;
import static ujpc.util.Lists.last;
import static ujpc.util.Lists.rest;

public class Sequence<InputT, ResultT>
       implements Parser<InputT, List<ResultT>> {
    private final List<Parser<InputT, ResultT>> parsers;

    private Sequence(List<Parser<InputT, ResultT>> parsers) {
        this.parsers = parsers;
    }

    @SafeVarargs
    public Sequence(Parser<InputT, ResultT>... parsers) {
        this.parsers = List.of(parsers);
    }

    @Override
    public State<InputT, List<ResultT>>
           parse(State.Success<InputT, List<ResultT>> s) {
        if (parsers.isEmpty())
            return s;

        if (s.result == null)
            return parse(new State.Success<InputT, List<ResultT>>(
                s.input, List.of()));

        State.Success<InputT, ResultT> state =
            new State.Success<InputT, ResultT>(
                s.input,
                s.result.isEmpty() ? null : last(s.result));

        return first(parsers).parse(state)
            .match(success -> new Sequence<InputT, ResultT>(rest(parsers))
                                  .parse(new State.Success<InputT, List<ResultT>>(
                                         success.input,
                                         success.result == null
                                             ? s.result
                                             : append(s.result, success.result))),
                   failure -> new State.Failure<InputT, List<ResultT>>(
                                  String.format("Expected %s but got %s.",
                                                first(parsers), state.input)));
    }
}
