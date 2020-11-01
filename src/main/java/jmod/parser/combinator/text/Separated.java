package jmod.parser.combinator.text;

import jmod.parser.Parser;
import jmod.parser.State;

import static jmod.util.Lists.last;
import static jmod.util.Lists.append;

import java.util.List;

public class Separated<ResultT> implements Parser<String, List<ResultT>> {
    private Parser<String, ResultT> parser;
    private String separator;
    private Token separatorParser;

    public Separated(String separator, Parser<String, ResultT> parser) {
        this.separator = separator;
        this.parser = parser;
        this.separatorParser = new Token(separator);
    }

    @Override
    public State<String, List<ResultT>>
    parse(State.Success<String, List<ResultT>> s) {
        if (s.result == null)
            return parse(new State.Success<String, List<ResultT>>(
                s.input, List.of()));

        State.Success<String, ResultT> state
            = new State.Success<>(
                s.input,
                s.result.isEmpty() ? null : last(s.result));

        return parser.parse(state)
            .match(elSuccess -> separatorParser.parse(
                   new State.Success<String, String>(elSuccess.input, null))
                   .match(sepSuccess -> parse(
                              new State.Success<String, List<ResultT>>(
                                  sepSuccess.input,
                                  elSuccess.result == null
                                      ? s.result
                                      : append(s.result, elSuccess.result))),
                          sepFailure -> new State.Success<String, List<ResultT>>(
                                            elSuccess.input,
                                            append(s.result, elSuccess.result))),
                   elFailure -> new State.Failure<String, List<ResultT>>(
                                    elFailure.error));

    }
}
