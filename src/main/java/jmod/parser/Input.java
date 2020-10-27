package jmod.parser;

public interface Input<T> {
    T        get();
    Input<T> take(int n);
    Input<T> skip(int n);
    boolean  eof();
}
