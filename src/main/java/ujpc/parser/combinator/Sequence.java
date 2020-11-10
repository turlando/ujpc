package ujpc.parser.combinator;

import java.util.List;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.Input;
import static ujpc.util.Lists.append;
import static ujpc.util.Lists.first;
import static ujpc.util.Lists.rest;

public class Sequence<InputT extends Input<?>, ResultT>
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
    public State<InputT, List<ResultT>> parse(InputT in) {
        return parse(in, List.of());
    }

    private State<InputT, List<ResultT>> parse(InputT in, List<ResultT> acc) {
        if (parsers.isEmpty())
            return new State.Success<InputT, List<ResultT>>(in, acc);

        return first(parsers).parse(in)
            .match(success -> new Sequence<InputT, ResultT>(rest(parsers))
                                  .parse(success.input(),
                                         success.result() == null
                                         ? acc
                                         : append(acc, success.result())),
                   failure -> new State.Failure<InputT, List<ResultT>>(
                                  in,
                                  String.format("Expected %s but got \"%s\".",
                                                first(parsers), in)));
    }
}
