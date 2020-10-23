package jmod.parser;

public class StringP {
    private final String target;
    public StringP(String target) { this.target = target; }

    public ParserState parse(String s, int index) throws ParserException {
        String substring = s.substring(index);
        if   (substring.startsWith(target)) return new ParserState(s, s.length());
        else throw new ParserException(String.format("Tried to match %s but got %s.",
                                                     substring, target));
    }
}
