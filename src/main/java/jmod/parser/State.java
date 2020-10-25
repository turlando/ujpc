package jmod.parser;

import java.util.List;
import java.util.function.Function;

public abstract class State {
    public final String input;
    public final int    index;

    protected State(String input, int index) {
        this.input = input;
        this.index = index;
    }

    public static Success initial(String input) {
        return new Success(input, 0, List.of());
    }

    public abstract <T> T match(Function<Success, T> success,
                                Function<Failure, T> failure);

    public static class Success extends State {
        public final List<String> result;

        public Success(String input, int index, List<String> result) {
            super(input, index);
            this.result = result;
        }

        public <T> T match(Function<Success, T> success,
                           Function<Failure, T> failure) {
            return success.apply(this);
        }

        public Success withResult(List<String> result) {
            return new Success(this.input, this.index, result);
        }

        public boolean equals(Object that) {
            if (that instanceof Success) return equals((Success) that);
            else return false;
        }

        public boolean equals(Success that) {
            return this.input.equals(that.input)
                && this.index == that.index
                && this.result.equals(that.result);
        }

        public String toString() {
            return String.format("Success(input=%s, index=%d, result=%s)",
                                 input, index, result);
        }
    }

    public static class Failure extends State {
        public final String error;

        public Failure(String input, int index, String error) {
            super(input, index);
            this.error = error;
        }

        public <T> T match(Function<Success, T> success,
                           Function<Failure, T> failure) {
            return failure.apply(this);
        }

        public String toString() {
            return String.format("Failure(input=%s, index=%d, error=%s)",
                                 input, index, error);
        }
    }
}
