package ujpc.parser.combinator.binary;

import ujpc.parser.Parser;
import ujpc.parser.State;

import static ujpc.util.Bytes.take;
import static ujpc.util.Bytes.drop;
import static ujpc.util.Bytes.first;

import java.nio.ByteBuffer;

public class UInt8 implements Parser<byte[], Integer> {
    @Override
    public State<byte[], Integer> parse(byte[] in) {
        return new State.Success<byte[], Integer>(drop(in, 1), (int) first(in));
    }
}
