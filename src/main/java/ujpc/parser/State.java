package ujpc.parser;

import java.util.List;
import java.util.function.Function;

public abstract class State<InputT, ResultT> {
    private final InputT input;
    private final int    offset;

    private State(InputT input, int offset) {
        this.input = input;
        this.offset = offset;
    }

    public          InputT input()  { return input; }
    public          int    offset() { return offset; }
    public abstract InputT rest();

    public abstract <T> T match(Function<Success<InputT, ResultT>, T> success,
                                Function<Failure<InputT, ResultT>, T> failure);

    public ResultT getOrThrow() {
        return match(success -> success.result(),
                     failure -> { throw new RuntimeException(failure.error); });
    }

    protected boolean equals(State that) {
        return this.input.equals(that.input)
            && this.offset == that.offset;
    }

    public static abstract class Success<InputT, ResultT>
    extends State<InputT, ResultT> {
        private final ResultT result;

        public Success(InputT input, int offset, ResultT result) {
            super(input, offset);
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
            return super.equals(that)
                && this.result.equals(that.result);
        }

        public String toString() {
            return String.format("Success(result=%s)", result);
        }
    }

    public final static abstract class Failure<InputT, ResultT>
    extends State<InputT, ResultT> {
        private final String error;

        public Failure(InputT input, int offset, String error) {
            super(input, offset);
            this.error = error;
        }

        public String error() { return error; }

        public <T> T match(Function<Success<InputT, ResultT>, T> success,
                           Function<Failure<InputT, ResultT>, T> failure) {
            return failure.apply(this);
        }

        public boolean equals(Object that) {
            if (that instanceof Failure)
                return equals((Failure) that);
            else return false;
        }

        public boolean equals(Failure that) {
            return super.equals(that)
                && this.error.equals(that.error);
        }

        public String toString() {
            return String.format("Failure(%s)", error);
        }
    }
}
