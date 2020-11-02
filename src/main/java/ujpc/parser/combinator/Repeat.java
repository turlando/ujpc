package ujpc.parser.combinator;

import java.util.List;

import ujpc.parser.Parser;
import ujpc.parser.State;
import static ujpc.util.Lists.append;
import static ujpc.util.Lists.first;
import static ujpc.util.Lists.last;
import static ujpc.util.Lists.rest;

public class Repeat<InputT, ResultT>
       implements Parser<InputT, List<ResultT>> {
    private final int count;
    private final Parser<InputT, ResultT> parser;

    public Repeat(int count, Parser<InputT, ResultT> parser) {
        this.count = count;
        this.parser = parser;
    }

    @Override
    public State<InputT, List<ResultT>>
           parse(State.Success<InputT, List<ResultT>> s) {
        if (count == 0)
            return s;

        if (s.result == null)
            return parse(new State.Success<InputT, List<ResultT>>(
                s.input, List.of()));

        State.Success<InputT, ResultT> state =
            new State.Success<InputT, ResultT>(
                s.input,
                s.result.isEmpty() ? null : last(s.result));

        return parser.parse(state)
            .match(success -> new Repeat<InputT, ResultT>(count - 1, parser)
                                  .parse(new State.Success<InputT, List<ResultT>>(
                                         success.input,
                                         success.result == null
                                             ? s.result
                                             : append(s.result, success.result))),
                   failure -> new State.Failure<InputT, List<ResultT>>(
                                  failure.error));
    }
}
