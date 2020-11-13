package ujpc.parser.combinator;

import java.util.List;
import java.util.stream.Collectors;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.Input;
import static ujpc.util.Lists.first;
import static ujpc.util.Lists.rest;
import static ujpc.util.Lists.append;

public class Choice<InputT extends Input<?>, ResultT>
implements Parser<InputT, ResultT> {
    private final List<Parser<InputT, ResultT>> parsers;

    private Choice(List<Parser<InputT, ResultT>> parsers) {
        this.parsers = parsers;
    }

    @SafeVarargs
    public Choice(Parser<InputT, ResultT>... parsers) {
        this(List.of(parsers));
    }

    private State<InputT, ResultT>parse(InputT in,
                                        List<Parser<InputT, ResultT>> parsersToTry,
                                        State.Failure<InputT, ResultT> bestState) {
        if (parsersToTry.isEmpty())
            return new State.Failure<InputT, ResultT>(
                bestState.input(),
                bestState.error() == null
                ? String.format("Expected: %s", toString())
                : bestState.error());
        else
            return first(parsersToTry).parse(in)
                .match(success -> success,
                       failure -> parse(
                           in,
                           rest(parsersToTry),
                           bestState == null || failure.input().offset()
                                                > bestState.input().offset()
                           ? failure
                           : bestState));
    }

    @Override
    public State<InputT, ResultT> parse(InputT in) {
        return parse(in, parsers, new State.Failure<InputT, ResultT>(in, null));
    }

    public String toString() {
        return String.join(
            " or ",
            parsers.stream().map(x -> x.toString())
                   .collect(Collectors.toList()));
    }
}
