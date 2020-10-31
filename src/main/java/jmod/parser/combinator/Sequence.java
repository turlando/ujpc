package jmod.parser.combinator;

import java.util.List;

import jmod.parser.Parser;
import jmod.parser.State;
import static jmod.util.Lists.append;
import static jmod.util.Lists.first;
import static jmod.util.Lists.last;
import static jmod.util.Lists.rest;

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

        State.Success<InputT, ResultT> state =
            new State.Success<InputT, ResultT>(
                s.input,
                s.result == null || s.result.isEmpty()
                    ? null
                    : last(s.result));

        return first(parsers).parse(state)
            .match(success -> new Sequence<InputT, ResultT>(rest(parsers))
                                  .parse(new State.Success<InputT, List<ResultT>>(
                                         success.input,
                                         success.result == null
                                             ? s.result == null
                                                 ? List.of()
                                                 : s.result
                                             : append(s.result == null
                                                          ? List.of()
                                                          : s.result,
                                                      success.result))),
                   failure -> new State.Failure<InputT, List<ResultT>>(
                                  String.format("Expected %s but got %s.",
                                                first(parsers), state.input)));
    }
}