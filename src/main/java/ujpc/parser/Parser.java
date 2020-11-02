package ujpc.parser;

import java.util.function.Function;

public interface Parser<InputT, ResultT> {
    State<InputT, ResultT> parse(State.Success<InputT, ResultT> s);

    default <NewT> Parser<InputT, NewT>
    map(Function<ResultT, NewT> fn) {
        return new Parser<InputT, NewT>() {
            @Override
            public State<InputT, NewT> parse(State.Success<InputT, NewT> s) {
                State.Success<InputT, ResultT> initial
                    = new State.Success<>(s.input, null);
                return Parser.this.parse(initial)
                             .match(success ->
                                    new State.Success<InputT, NewT>(
                                        success.input,
                                        fn.apply(success.result)),
                                    failure -> new State.Failure<InputT, NewT>(
                                                   failure.error));
            }
        };
    }
}
