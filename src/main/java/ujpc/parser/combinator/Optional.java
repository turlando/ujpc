package ujpc.parser.combinator;

import ujpc.parser.State;
import ujpc.parser.Parser;

public class Optional<InputT, ResultT>
       implements Parser<InputT, ResultT> {
    private final Parser<InputT, ResultT> parser;
    private final ResultT otherwise;

    public Optional(Parser<InputT, ResultT> parser,
                    ResultT otherwise) {
        this.parser = parser;
        this.otherwise = otherwise;
    }

    @Override
    public State<InputT, ResultT> parse(State.Success<InputT, ResultT> s) {
        return parser.parse(s)
            .match(success -> success,
                   failure -> new State.Success<InputT, ResultT>(
                                  s.input, otherwise));
    }
}
