package ujpc.parser.binary.combinator;

import java.nio.ByteBuffer;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.binary.Binary;

public class UInt16 implements Parser<Binary, Integer> {
    @Override
    public State<Binary, Integer> parse(Binary in) {
        return new State.Success<>(
            in.addOffset(2),
            (int) ByteBuffer.wrap(in.take(2)).getShort());
    }
}
