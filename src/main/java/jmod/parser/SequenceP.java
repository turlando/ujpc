package jmod.parser;

import java.util.List;
import java.util.LinkedList;

public class SequenceP implements Parser {
    public final List<Parser> parsers;
    public SequenceP(List<Parser> parsers) { this.parsers = parsers; }

    @Override
    public ParserState parse(ParserState state) throws ParserException {
        List<String> results = new LinkedList<String>();
        ParserState currentState = state;

        for (Parser parser : parsers) {
            currentState = parser.parse(currentState);
            results.addAll(currentState.result);
        }

        return new ParserState(currentState.input, results, currentState.index);
    }
}
