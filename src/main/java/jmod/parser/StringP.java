package jmod.parser;

public class StringP {
    private final String target;
    public StringP(String target) { this.target = target; }

    public String parse(String s, int index) throws ParserException {
        String substring = s.substring(index);
        if   (substring.startsWith(target)) return s;
        else throw new ParserException(String.format("Tried to match %s but got %s.",
                                                     substring, target));
    }
}
