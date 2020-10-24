package jmod.parser;

import java.util.List;

public class ParserState {
    public final String       input;
    public final List<String> result;
    public final int          index;

    public ParserState(String input) {
        this(input, List.of(), 0);
    }

    public ParserState(String input, List<String> result, int index) {
        this.input  = input;
        this.result = result;
        this.index  = index;
    }

    public boolean equals(ParserState that) {
        return this.input.equals(that.input)
            && this.result.equals(that.result)
            && this.index  == that.index;
    }

    @Override
    public String toString() {
        return String.format("ParserState(input=%s, result=%s, index=%d)",
                             input, result, index);
    }
}
