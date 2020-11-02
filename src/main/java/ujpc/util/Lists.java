package ujpc.util;

import java.util.stream.Stream;
import java.util.stream.Collectors;

public final class Lists {
    private Lists() {}

    public static <T> java.util.List<T> concat(java.util.List<T> l1,
                                               java.util.List<T> l2) {
        return java.util.List.copyOf(Stream.concat(l1.stream(), l2.stream())
                                           .collect(Collectors.toList()));
    }

    public static <T> java.util.List<T> append(java.util.List<T> l, T x) {
        return concat(l, java.util.List.of(x));
    }

    public static <T> T first(java.util.List<T> l) {
            return l.get(0);
    }

    public static <T> T last(java.util.List<T> l) {
        return l.get(l.size() - 1);
    }

    public static <T> java.util.List<T> rest(java.util.List<T> l) {
        return java.util.List.copyOf(l.stream().skip(1)
                                      .collect(Collectors.toList()));
    }
}
