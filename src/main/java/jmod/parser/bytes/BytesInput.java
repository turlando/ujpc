package jmod.parser.bytes;

import java.util.Arrays;
import jmod.parser.Input;

public class BytesInput implements Input<byte[]> {
    private byte[] input;

    public BytesInput(byte[] input) {
        this.input = input;
    }

    @Override
    public byte[] get() {
        return input;
    }

    @Override
    public BytesInput take(int n) {
        final int size = Math.min(n ,input.length);
        return new BytesInput(Arrays.copyOfRange(input, 0, size));
    }

    @Override
    public BytesInput skip(int n) {
        if (n > input.length)
            return new BytesInput(new byte[0]);
        return new BytesInput(Arrays.copyOfRange(input, n, input.length - 1));
    }

    @Override
    public boolean eof() {
        return input.length == 0;
    }
}
