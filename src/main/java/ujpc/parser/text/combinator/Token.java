package ujpc.parser.text.combinator;

import java.util.regex.Pattern;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;
import static ujpc.util.Lists.first;

public class Token implements Parser<Text, String> {
    private String target;
    private Parser<Text, String> parser;

    public Token(String target) {
        this.target = target;
        final String patternString = String.format("(%s)", Pattern.quote(target));
        final Pattern pattern = Pattern.compile(patternString);
        this.parser = new Regex(pattern).map(result -> first(result));
    }

    @Override
    public State<Text, String> parse(Text in) {
        State<Text, String> result = parser.parse(in);

        return result.match(
            success -> success,
            failure -> new State.Failure<>(
                           in,
                           String.format("Expected token: \"%s\".", target)));
    }

    @Override
    public String toString() {
        return String.format("Token(\"%s\")", target);
    }
}
