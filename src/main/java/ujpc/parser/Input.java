package ujpc.parser;

public abstract class Input<T> {
    protected final T   input;
    protected final int offset;

    public Input(T input, int offset) {
        this.input  = input;
        this.offset = offset;
    }

    public Input(T input) { this(input, 0); }

    public          T   input()  { return input; }
    public          int offset() { return offset; }
    public abstract T   rest();

    public abstract Input<T> addOffset(int offset);
    public abstract String needle();
}
