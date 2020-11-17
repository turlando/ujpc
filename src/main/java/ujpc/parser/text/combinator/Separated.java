package ujpc.parser.text.combinator;

import java.util.List;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;
import static ujpc.util.Lists.append;

public class Separated<ResultT> implements Parser<Text, List<ResultT>> {
    private final Parser<Text, String> separatorParser;
    private final Parser<Text, ResultT> parser;

    public Separated(Parser<Text, String> separatorParser,
                     Parser<Text, ResultT> parser) {
        this.separatorParser = separatorParser;
        this.parser = parser;
    }

    @Override
    public State<Text, List<ResultT>> parse(Text in) {
        return parse(in, List.of());
    }

    private State<Text, List<ResultT>> parse(Text in, List<ResultT> acc) {
        return parser.parse(in).match(
            elSuccess -> separatorParser.parse(elSuccess.input()).match(
                sepSuccess -> parse(sepSuccess.input(),
                                    elSuccess.result() == null
                                    ? acc
                                    : append(acc, elSuccess.result())),
                sepFailure -> new State.Success<>(
                                  sepFailure.input(),
                                  append(acc, elSuccess.result()))),
            elFailure -> new State.Failure<>(elFailure.input(),
                                             elFailure.error()));
    }

    public String toString() {
        return String.format("%s separated by %s",
                             parser, separatorParser);
    }
}
