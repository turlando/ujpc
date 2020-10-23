package jmod.parser;

public class StringP {
    private final String target;
    public StringP(String target) { this.target = target; }

    public ParserState parse(ParserState state) throws ParserException {
        String substring = state.input.substring(state.index);
        if (substring.startsWith(target))
            return new ParserState(state.input,
                                   target,
                                   state.index + target.length());
        else throw new ParserException(String.format("Tried to match %s but got %s.",
                                                     substring, target));
    }
}
