package ujpc.parser.combinator.binary;

import ujpc.parser.Parser;
import ujpc.parser.State;

import static ujpc.util.Bytes.take;
import static ujpc.util.Bytes.drop;
import static ujpc.util.Bytes.first;

import java.nio.ByteBuffer;

public class UInt8 implements Parser<byte[], Integer> {
    @Override
    public State<byte[], Integer> parse(State.Success<byte[], Integer> s) {
        return new State.Success<byte[], Integer>(
            drop(s.input, 1),
            (int) first(s.input));
    }
}
