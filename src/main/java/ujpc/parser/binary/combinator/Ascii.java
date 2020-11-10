package ujpc.parser.binary.combinator;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.binary.Binary;
import static ujpc.util.Bytes.take;

public class Ascii implements Parser<Binary, String> {
    private final int length;

    public Ascii(int length) {
        this.length = length;
    }

    @Override
    public State<Binary, String> parse(Binary in) {
        try {
            return new State.Success<>(
                in.addOffset(length),
                new String(in.take(length), "US-ASCII").trim());
        } catch (java.io.UnsupportedEncodingException e) {
            return new State.Failure<Binary, String>(in, e.toString());
        }
    }
}
