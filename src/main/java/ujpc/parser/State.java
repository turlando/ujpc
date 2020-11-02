package ujpc.parser;

import java.util.List;
import java.util.function.Function;

public abstract class State<InputT, ResultT> {
    private State() {}

    public abstract <T> T match(Function<Success<InputT, ResultT>, T> success,
                                Function<Failure<InputT, ResultT>, T> failure);

    public final static class Success<InputT, ResultT>
                        extends State<InputT, ResultT> {
        public final InputT input;
        public final ResultT result;

        public Success(InputT input, ResultT result) {
            this.input = input;
            this.result = result;
        }

        public <T> T match(Function<Success<InputT, ResultT>, T> success,
                           Function<Failure<InputT, ResultT>, T> failure) {
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

    public final static class Failure<InputT, ResultT>
                        extends State<InputT, ResultT> {
        public final String error;

        public Failure(String error) {
            this.error = error;
        }

        public <T> T match(Function<Success<InputT, ResultT>, T> success,
                           Function<Failure<InputT, ResultT>, T> failure) {
            return failure.apply(this);
        }

        public String toString() {
            return String.format("Failure(%s)", error);
        }
    }
}
