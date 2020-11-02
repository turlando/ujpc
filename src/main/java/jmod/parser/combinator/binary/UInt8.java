package jmod.parser.combinator.binary;

import jmod.parser.Parser;
import jmod.parser.State;

import static jmod.util.Bytes.take;
import static jmod.util.Bytes.drop;
import static jmod.util.Bytes.first;

import java.nio.ByteBuffer;

public class UInt8 implements Parser<byte[], Integer> {
    @Override
    public State<byte[], Integer> parse(State.Success<byte[], Integer> s) {
        return new State.Success<byte[], Integer>(
            drop(s.input, 1),
            (int) first(s.input));
    }
}
