package ujpc.parser.binary.combinator;

import java.nio.ByteBuffer;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.binary.Binary;

public class UInt32 implements Parser<Binary, Integer> {
    @Override
    public State<Binary, Integer> parse(Binary in) {
        return new State.Success<Binary, Integer>(
            in.addOffset(4),
            (int) ByteBuffer.wrap(in.take(4)).getInt());
    }
}
