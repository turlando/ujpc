package ujpc.parser.combinator;

import ujpc.parser.State;
import ujpc.parser.Parser;
import ujpc.parser.Input;

public class Optional<InputT extends Input<?>, ResultT>
implements Parser<InputT, ResultT> {
    private final Parser<InputT, ResultT> parser;
    private final ResultT otherwise;

    public Optional(Parser<InputT, ResultT> parser,
                    ResultT otherwise) {
        this.parser = parser;
        this.otherwise = otherwise;
    }

    @Override
    public State<InputT, ResultT> parse(InputT in) {
        return parser.parse(in)
            .match(success -> success,
                   failure -> new State.Success<InputT, ResultT>(in, otherwise));
    }
}
