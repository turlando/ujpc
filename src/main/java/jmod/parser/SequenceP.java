package jmod.parser;

import java.util.List;

public class SequenceP {
    public final List<StringP> parsers;
    public SequenceP(List<StringP> parsers) { this.parsers = parsers; }

    public ParserState parse(ParserState state) throws ParserException {
        List<String> results = List.of();
        ParserState currentState = state;

        for (StringP parser : parsers) {
            currentState = parser.parse(currentState);
            results.addAll(currentState.result);
        }

        return new ParserState(currentState.input, results, currentState.index);
    }
}
