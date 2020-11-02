package ujpc.parser.combinator.text;

import ujpc.parser.Parser;
import ujpc.parser.State;

import static ujpc.util.Lists.first;
import static ujpc.util.Strings.take;
import static ujpc.util.Strings.drop;

import java.util.regex.Pattern;

public class Token implements Parser<String, String> {
    private String target;
    private Parser<String, String> parser;

    public Token(String target) {
        this.target = target;
        this.parser = new Regex(String.format("(%s)", Pattern.quote(target)))
                          .map(result -> first(result));
    }

    @Override
    public State<String, String> parse(State.Success<String, String> state) {
        State<String, String> result = parser.parse(state);

        return result.match(
            success -> success,
            failure -> new State.Failure<String, String>(
                           String.format("Tried to match \"%s\" but got \"%s\".",
                                         target, state.input)));
    }

    @Override
    public String toString() {
        return String.format("Token(\"%s\")", target);
    }
}
