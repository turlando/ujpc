package jmod.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class Maps {
    private Maps() {}

    public static <K, V> Map<K, V> merge(Map<K, V> m1, Map<K, V> m2) {
        return Stream.concat(m1.entrySet().stream(),
                             m2.entrySet().stream())
                     .collect(Collectors.toMap(Map.Entry::getKey,
                                               Map.Entry::getValue));
    }

    public static <K, V> Map<K, V> merge(List<Map<K, V>> maps) {
        return maps.stream().reduce(Map.of(), Maps::merge);
    }
}
