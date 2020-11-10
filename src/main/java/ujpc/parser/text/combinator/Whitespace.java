package ujpc.parser.text.combinator;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;

import static ujpc.util.Lists.first;

public class Whitespace implements Parser<Text, String> {
    private final Parser<Text, String> parser
        = new Regex("(\\s*)").map(x -> first(x));

    @Override
    public State<Text, String> parse(Text in) {
        return parser.parse(in);
    }
}
