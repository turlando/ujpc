package jmod.parser.text;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

import jmod.parser.Parser;
import jmod.parser.State;
import static jmod.util.Strings.drop;
import static jmod.util.Lists.append;


public class Regex implements Parser<String, List<String>> {
    private final Pattern target;

    public Regex(String target)  { this.target = Pattern.compile(target); }
    public Regex(Pattern target) { this.target = target; }

    @Override
    public State<String, List<String>>
    parse(State.Success<String, List<String>> state) {
        Matcher matcher = target.matcher(state.input);

        if (matcher.lookingAt())
            return new State.Success<String, List<String>>(
                drop(state.input, matcher.end()),
                matchResultToStrings(matcher.toMatchResult()));
        else
            return new State.Failure<String, List<String>>(
                String.format("Tried to match %s but got %s.",
                              target, state.input));
    }

    @Override
    public String toString() {
        return String.format("Regex(%s)", target.toString());
    }

    private static List<String> matchResultToStrings(MatchResult mr) {
        List<String> result = List.of();
        for (int i = 0; i < mr.groupCount(); i++)
            result = append(result, mr.group(i));
        return result;
    }
}
