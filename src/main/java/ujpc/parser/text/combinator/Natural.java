package ujpc.parser.text.combinator;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;
import static ujpc.util.Lists.first;

import java.util.regex.Pattern;

public class Natural implements Parser<Text, String> {
    private static final Parser<Text, String> parser
        = new Regex("([+-]?[0-9]+)").map(x -> first(x));

    @Override
    public State<Text, String> parse(Text in) {
        State<Text, String> result = parser.parse(in);

        return result.match(
            success -> success,
            failure -> new State.Failure<>(
                in,
                String.format("Expected: %s", toString())));
    }

    @Override
    public String toString() {
        return "natural number";
    }
}
