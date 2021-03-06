package ujpc.parser;

public abstract class Input<T> {
    private final T   input;
    private final int offset;

    protected Input(T input) { this(input, 0); }

    protected Input(T input, int offset) {
        this.input  = input;
        this.offset = offset;
    }

    public          T   input()  { return input; }
    public          int offset() { return offset; }

    public abstract T rest();
    public abstract T take(int n);
    public abstract T drop(int n);

    public abstract Input<T> addOffset(int offset);

    public abstract String position();
    public abstract String needle();
}
