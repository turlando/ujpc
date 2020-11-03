package ujpc.parser.combinator.text;

import static ujpc.util.Lists.first;
import ujpc.parser.Parser;
import ujpc.parser.State;

public class Int implements Parser<String, Integer> {
    private Parser<String, Integer> parser
        = new Regex("([+-]?[0-9]+)")
              .map(result -> Integer.parseInt(first(result)));

    @Override
    public State<String, Integer> parse(String in) {
        return parser.parse(in);
    }
}
