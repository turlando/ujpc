package ujpc.util;

import java.util.Arrays;

public final class Bytes {
    private Bytes() {}

    public static byte[] take(byte[] b, int n) {
        return Arrays.copyOf(b, n);
    }

    public static byte[] take(byte[] b, int from, int to) {
        return Arrays.copyOfRange(b, from, to);
    }

    public static byte[] drop(byte[] b, int n) {
        return Arrays.copyOfRange(b, n, b.length);
    }

    public static byte[] drop(byte[] b, int from, int to) {
        return Arrays.copyOfRange(b, from, to);
    }

    public static byte first(byte[] b) {
        return b[0];
    }
}
