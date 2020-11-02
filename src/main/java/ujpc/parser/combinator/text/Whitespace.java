package ujpc.parser.combinator.text;

import ujpc.parser.Parser;
import ujpc.parser.State;

import static ujpc.util.Lists.first;

public class Whitespace implements Parser<String, String> {
    private final Parser<String, String> parser
        = new Regex("(\\s*)").map(x -> first(x));

    @Override
    public State<String, String> parse(State.Success<String, String> s) {
        return parser.parse(s);
    }
}
