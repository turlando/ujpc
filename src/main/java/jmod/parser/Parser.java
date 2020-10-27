package jmod.parser;

import java.util.function.BiFunction;

public interface Parser<T extends Input, R> {
    State<T, R> parse(State.Success<T, R> s,
                      BiFunction<R, T, R> transformer);
}
