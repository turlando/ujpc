package jmod.parser.string;

import java.util.List;
import java.util.function.BiFunction;

import jmod.parser.Parser;
import jmod.parser.State;

public class Str<T> implements Parser<StringInput, T> {
    private final String target;
    public Str(String target) { this.target = target; }

    @Override
    public State parse(State.Success<StringInput, T> state,
                       BiFunction<T, StringInput, T> transformer) {
        int size = target.length();
        StringInput substring = state.input.take(size);

        if (substring.get().equals(target))
            return new State.Success(state.input.skip(size),
                                     transformer.apply(state.result, substring));
        else
            return new State.Failure(
                String.format("Tried to match \"%s\" but got \"%s\".",
                              target, substring.get()));
    }
}
