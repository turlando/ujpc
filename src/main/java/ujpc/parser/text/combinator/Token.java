package ujpc.parser.text.combinator;

import java.util.regex.Pattern;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;
import static ujpc.util.Strings.getCommonPrefix;
import static ujpc.util.Lists.first;

public class Token implements Parser<Text, String> {
    private final String target;
    private final Parser<Text, String> parser;

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
            failure -> {
                String common = getCommonPrefix(new String[] {target, in.rest()});
                if (common == null)
                    return new State.Failure<>(
                        in,
                        String.format("Expected: %s", target));
                    else
                        return new State.Failure<>(
                            in.addOffset(common.length()),
                            String.format("Expected %s, found %s",
                                          target, in.take(target.length())));
            });
    }

    @Override
    public String toString() {
        return target;
    }
}
