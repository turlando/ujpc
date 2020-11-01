package jmod.parser.combinator.text;

import jmod.parser.Parser;
import jmod.parser.State;

import static jmod.util.Lists.first;

public class Whitespace implements Parser<String, String> {
    private final Parser<String, String> parser
        = new Regex("(\\s*)").map(x -> first(x));

    @Override
    public State<String, String> parse(State.Success<String, String> s) {
        return parser.parse(s);
    }
}
