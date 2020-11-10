package ujpc.parser.binary;

import ujpc.parser.Input;
import ujpc.util.Bytes;

public class Binary
extends Input<byte[]> {
    public Binary(byte[] input)             { super(input, 0); }
    public Binary(byte[] input, int offset) { super(input, offset); }

    @Override public byte[] rest()      { return Bytes.drop(input(), offset()); }
    @Override public byte[] take(int n) { return Bytes.take(input(), offset(), n); }
    @Override public byte[] drop(int n) { return Bytes.drop(input(), offset(), n); }

    @Override public String position()  { return String.format("%X", offset()); }
    @Override public String needle()    { return "To be implemented."; }

    @Override public Binary addOffset(int offset)
        { return new Binary(input(), offset() + offset); }
}
