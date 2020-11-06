package ujpc.parser.combinator.text;

import ujpc.parser.Parser;
import ujpc.parser.State;

import static ujpc.util.Lists.first;
import static ujpc.util.Strings.take;
import static ujpc.util.Strings.drop;

import java.util.regex.Pattern;

public class Alphanum implements Parser<String, String> {
    private Parser<String, String> parser
        = new Regex("([a-zA-Z0-9]+)").map(x -> first(x));

    @Override
    public State<String, String> parse(String in) {
        State<String, String> result = parser.parse(in);

        return result.match(
            success -> success,
            failure -> new State.Failure<String, String>(
                           String.format("\"%s\" is not alphanumeric.", in)));
    }

    @Override
    public String toString() {
        return "Alphanum()";
    }
}
