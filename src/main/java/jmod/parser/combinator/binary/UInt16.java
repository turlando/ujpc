package jmod.parser.combinator.binary;

import jmod.parser.Parser;
import jmod.parser.State;

import static jmod.util.Bytes.take;
import static jmod.util.Bytes.drop;

import java.nio.ByteBuffer;

public class UInt16 implements Parser<byte[], Integer> {
    @Override
    public State<byte[], Integer> parse(State.Success<byte[], Integer> s) {
        return new State.Success<byte[], Integer>(
            drop(s.input, 2),
            (int) ByteBuffer.wrap(take(s.input, 2)).getShort());
    }
}
