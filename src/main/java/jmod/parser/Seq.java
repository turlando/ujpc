package jmod.parser;

import java.util.List;
import java.util.LinkedList;

import static jmod.util.List.append;
import static jmod.util.List.concat;
import static jmod.util.List.getLast;
import static jmod.util.List.rest;

public class Seq implements Parser {
    public final List<Parser> parsers;

    public Seq(Parser... parsers)    { this.parsers = List.of(parsers); }
    public Seq(List<Parser> parsers) { this.parsers = parsers; }

    @Override
    public State parse(State.Success state) {
        if (parsers.isEmpty())
            return state;

        return parsers.get(0)
                      .parse(state)
                      .match(success -> new Seq(rest(parsers))
                                               .parse(success.withResult(
                                                   concat(state.result,
                                                          success.result))),
                              failure -> failure);
    }
}
