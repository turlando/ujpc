package jmod.parser.text;

import jmod.parser.Parser;
import jmod.parser.State;

import static jmod.util.Strings.take;
import static jmod.util.Strings.drop;

public class Token implements Parser<String, String> {
    private final String target;
    public Token(String target) { this.target = target; }

    @Override
    public State<String, String> parse(State.Success<String, String> state) {
        int size = target.length();
        String substring = take(state.input, size);

        if (substring.equals(target))
            return new State.Success<String, String>(drop(state.input, size),
                                     substring);
        else
            return new State.Failure<String, String>(
                String.format("Tried to match \"%s\" but got \"%s\".",
                              target, substring));
    }

    @Override
    public String toString() {
        return String.format("Token(\"%s\")", target);
    }
}
