package jmod.parser;

public interface Parser {
    ParserState parse(ParserState s) throws ParserException;
}
