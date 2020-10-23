package jmod.parser;

public class ParserState {
    public final String result;
    public final int index;

    public ParserState(String result, int index) {
        this.result = result;
        this.index = index;
    }

    public boolean equals(ParserState that) {
        return this.result == that.result
            && this.index  == that.index;
    }

    @Override
    public String toString() {
        return String.format("ParserState(result=%s, index=%d)", result, index);
    }
}
