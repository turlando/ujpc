package ujpc.parser.combinator;

import java.util.List;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.Input;
import static ujpc.util.Lists.append;
import static ujpc.util.Lists.first;
import static ujpc.util.Lists.last;
import static ujpc.util.Lists.rest;

public class Repeat<InputT extends Input<?>, ResultT>
implements Parser<InputT, List<ResultT>> {
    private final int count;
    private final Parser<InputT, ResultT> parser;

    public Repeat(int count, Parser<InputT, ResultT> parser) {
        this.count = count;
        this.parser = parser;
    }

    @Override
    public State<InputT, List<ResultT>> parse(InputT in) {
        return parse(in, List.of());
    }

    private State<InputT, List<ResultT>> parse(InputT in, List<ResultT> acc) {
        if (count == 0)
            return new State.Success<InputT, List<ResultT>>(in, acc);

        return parser.parse(in)
            .match(success -> new Repeat<InputT, ResultT>(count - 1, parser)
                                  .parse(success.input(),
                                         success.result() == null
                                         ? acc
                                         : append(acc, success.result())),
                   failure -> new State.Failure<InputT, List<ResultT>>(
                                  in,
                                  // TODO improve error message
                                  failure.error()));
    }
}
