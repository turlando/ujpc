package ujpc.util;

public final class Strings {
    private Strings() {}

    public static String take(String s, int n) {
        final int end = Math.min(n, s.length());
        return s.substring(0, end);
    }

    public static String drop(java.lang.String s, int n) {
        if (n > s.length())
            return "";
        return s.substring(n, s.length());
    }

    /** Return the number of newlines in given string.
     */
    public static int newlines(String s) {
        return s.length() - s.replace("\n", "").length();
    }
}
