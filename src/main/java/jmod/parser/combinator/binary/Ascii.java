package jmod.parser.combinator.binary;

import jmod.parser.Parser;
import jmod.parser.State;

import static jmod.util.Bytes.take;
import static jmod.util.Bytes.drop;

public class Ascii implements Parser<byte[], String> {
    private final int length;

    public Ascii(int length) {
        this.length = length;
    }

    @Override
    public State<byte[], String> parse(State.Success<byte[], String> s) {
        try {
            return new State.Success<byte[], String>(
                drop(s.input, length),
                new String(take(s.input, length), "US-ASCII").trim());
        } catch (java.io.UnsupportedEncodingException e) {
            return new State.Failure<byte[], String>(e.toString());
        }
    }
}
