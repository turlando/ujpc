package ujpc.parser;

import java.util.function.Function;

public interface Parser<InputT, ResultT> {
    State<InputT, ResultT> parse(InputT in);

    default <NewT> Parser<InputT, NewT>
    map(Function<ResultT, NewT> fn) {
        return new Parser<InputT, NewT>() {
            @Override
            public State<InputT, NewT> parse(InputT in) {
                return Parser.this.parse(in).match(
                    success -> new State.Success<InputT, NewT>(
                                   success.input,
                                   fn.apply(success.result)),
                    failure -> new State.Failure<InputT, NewT>(failure.error));
            }
        };
    }

    default <NewT> Parser<InputT, NewT>
    bind(Function<ResultT, Parser<InputT, NewT>> fn) {
        return new Parser<InputT, NewT>() {
            @Override
            public State<InputT, NewT> parse(InputT in) {
                return Parser.this.parse(in).match(
                    success -> fn.apply(success.result).parse(success.input),
                    failure -> new State.Failure<InputT, NewT>(failure.error));
            }
        };
    }
}
