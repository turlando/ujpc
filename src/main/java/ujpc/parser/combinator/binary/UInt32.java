package ujpc.parser.combinator.binary;

import ujpc.parser.Parser;
import ujpc.parser.State;

import static ujpc.util.Bytes.take;
import static ujpc.util.Bytes.drop;

import java.nio.ByteBuffer;

public class UInt32 implements Parser<byte[], Integer> {
    @Override
    public State<byte[], Integer> parse(byte[] in) {
        return new State.Success<byte[], Integer>(
            drop(in, 4),
            (int) ByteBuffer.wrap(take(in, 4)).getShort());
    }
}
