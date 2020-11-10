package ujpc.parser.text.combinator;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;
import static ujpc.util.Lists.first;

public class Alphanum implements Parser<Text, String> {
    private Parser<Text, String> parser
        = new Regex("([a-zA-Z0-9]+)").map(x -> first(x));

    @Override
    public State<Text, String> parse(Text in) {
        State<Text, String> result = parser.parse(in);

        return result.match(
            success -> success,
            failure -> new State.Failure<>(
                in,
                "Expected alphanumeric string"));
    }

    @Override
    public String toString() {
        return "Alphanum()";
    }
}
