package ujpc.parser.combinator.text;

import ujpc.parser.Parser;
import ujpc.parser.State;

import static ujpc.util.Lists.last;
import static ujpc.util.Lists.append;

import java.util.List;

public class Separated<ResultT> implements Parser<String, List<ResultT>> {
    private Parser<String, String> separatorParser;
    private Parser<String, ResultT> parser;

    public Separated(Parser<String, String> separatorParser,
                     Parser<String, ResultT> parser) {
        this.separatorParser = separatorParser;
        this.parser = parser;
    }

    @Override
    public State<String, List<ResultT>> parse(String in) {
        return parse(in, List.of());
    }

    private State<String, List<ResultT>> parse(String in, List<ResultT> acc) {
        return parser.parse(in).match(
            elSuccess -> separatorParser.parse(elSuccess.input).match(
                sepSuccess -> parse(sepSuccess.input,
                                    elSuccess.result == null
                                    ? acc
                                    : append(acc, elSuccess.result)),
                sepFailure -> new State.Success<String, List<ResultT>>(
                                  elSuccess.input,
                                  append(acc, elSuccess.result))),
            elFailure -> new State.Failure<String, List<ResultT>>(
                                  elFailure.error));
    }

    public String toString() {
        return String.format("Separated(by=%s, in=%s)", separatorParser, parser);
    }
}
