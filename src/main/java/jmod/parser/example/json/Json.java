package jmod.parser.example.json;

import java.util.List;
import java.util.Map;
import java.util.Collections;

public abstract class Json {
    private Json() {}

    public static class Null extends Json {
        public boolean equals(java.lang.Object o) { return o instanceof Null; }
        public java.lang.String toString() { return "null"; }
    }

    public static class Boolean extends Json {
        public final boolean value;
        public Boolean(boolean value) { this.value = value; }
        public boolean equals(Boolean that) { return this.value == that.value; }
        public boolean equals(java.lang.Object o)
            { return o instanceof Boolean ? equals((Boolean) o) : false; }
        public java.lang.String toString()
            { return java.lang.String.valueOf(value); }
    }

    public static class Number extends Json {
        public final double value;
        public Number(double value) { this.value = value; }
        public boolean equals(Number that) { return this.value == that.value; }
        public boolean equals(java.lang.Object o)
            { return o instanceof Number ? equals((Number) o) : false; }
        public java.lang.String toString()
            { return java.lang.String.valueOf(value); }
    }

    public static class String extends Json {
        public final java.lang.String value;
        public String(java.lang.String value) { this.value = value; }
        public boolean equals(String that)
            { return this.value.equals(that.value); }
        public boolean equals(java.lang.Object o)
            { return o instanceof String ? equals((String) o) : false; }
        public java.lang.String toString() { return value; }
    }

    public static class Array extends Json {
        public final List<Json> value;
        public Array() { this.value = List.of(); }
        public Array(List<Json> value) { this.value = value; }
        public Array(Json... values) { this.value = List.of(values); }
        public boolean isEmpty() { return value.isEmpty(); }
        public boolean equals(Array that)
            { return this.value.equals(that.value); }
        public boolean equals(java.lang.Object o)
            { return o instanceof Array ? equals((Array) o) : false; }
        public java.lang.String toString() { return value.toString(); }
    }

    // TODO
    public static class Object extends Json {
        public final Map<String, Json> value;
        public Object(Map<String, Json> value) { this.value = value; }
        public Object() { this.value = Collections.emptyMap(); }
    }
}
