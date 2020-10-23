package jmod.parser;

public class ParserState {
    public final String input;
    public final String result;
    public final int    index;

    public ParserState(String input) {
        this(input, null, 0);
    }

    public ParserState(String input, String result, int index) {
        this.input  = input;
        this.result = result;
        this.index  = index;
    }

    public boolean equals(ParserState that) {
        return this.input == that.input
            && this.result == that.result
            && this.index  == that.index;
    }

    @Override
    public String toString() {
        return String.format("ParserState(input=%s, result=%s, index=%d)",
                             input, result, index);
    }
}
