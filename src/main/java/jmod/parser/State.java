package jmod.parser;

import java.util.List;
import java.util.function.Function;

public abstract class State<InputT extends Input, ResultT> {
    private State() {}

    public abstract <T> T match(Function<Success, T> success,
                                Function<Failure, T> failure);

    public static class Success<InputT extends Input, ResultT>
                        extends State<InputT, ResultT> {
        public final InputT input;
        public final ResultT result;

        public Success(InputT input, ResultT result) {
            this.input = input;
            this.result = result;
        }

        public <T> T match(Function<Success, T> success,
                           Function<Failure, T> failure) {
            return success.apply(this);
        }

        public boolean equals(Object that) {
            if (that instanceof Success) return equals((Success) that);
            else return false;
        }

        public boolean equals(Success that) {
            return this.input.equals(that.input)
                && this.result.equals(that.result);
        }

        public String toString() {
            return String.format("Success(input=%s, result=%s)",
                                 input, result);
        }
    }

    public static class Failure<InputT extends Input, ResultT>
                  extends State<InputT, ResultT> {
        public final String error;

        public Failure(String error) {
            this.error = error;
        }

        public <T> T match(Function<Success, T> success,
                           Function<Failure, T> failure) {
            return failure.apply(this);
        }

        public String toString() {
            return String.format("Failure(%s)", error);
        }
    }
}
