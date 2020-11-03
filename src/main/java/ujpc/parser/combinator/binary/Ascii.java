package ujpc.parser.combinator.binary;

import ujpc.parser.Parser;
import ujpc.parser.State;

import static ujpc.util.Bytes.take;
import static ujpc.util.Bytes.drop;

public class Ascii implements Parser<byte[], String> {
    private final int length;

    public Ascii(int length) {
        this.length = length;
    }

    @Override
    public State<byte[], String> parse(byte[] in) {
        try {
            return new State.Success<byte[], String>(
                drop(in, length),
                new String(take(in, length), "US-ASCII").trim());
        } catch (java.io.UnsupportedEncodingException e) {
            return new State.Failure<byte[], String>(e.toString());
        }
    }
}
