package jmod.parser.text;

import static jmod.util.Lists.first;
import jmod.parser.Parser;
import jmod.parser.State;

public class Int implements Parser<String, Integer> {
    private Parser<String, Integer> parser
        = new Regex("([+-]?[0-9]+)")
              .map(result -> Integer.parseInt(first(result)));

    @Override
    public State<String, Integer> parse(State.Success<String, Integer> s) {
        return parser.parse(s);
    }
}
