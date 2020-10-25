package jmod.parser;

import java.util.List;

public class Str implements Parser {
    private final String target;
    public Str(String target) { this.target = target; }

    @Override
    public State parse(State.Success state) {
        String substring = state.input.substring(state.index);

        if (substring.startsWith(target))
            return new State.Success(state.input,
                                     state.index + target.length(),
                                     List.of(target));
        else
            return new State.Failure(state.input,
                                     state.index,
                                     String.format("Tried to match %s but got %s.",
                                                   substring, target));
    }
}
