package ujpc.parser;

import java.util.function.Function;

public abstract class State<InputT extends Input<?>, ResultT> {
    private final InputT input;

    private State(InputT input) { this.input = input; }

    public InputT input() { return input; }

    public abstract <T> T match(Function<Success<InputT, ResultT>, T> success,
                                Function<Failure<InputT, ResultT>, T> failure);

    public ResultT getOrThrow() throws ParserException {
        ResultT result = match(success -> success.result(),
                               failure -> null);
        if (result == null)
            throw new ParserException(match(s -> null, f -> f));
        else
            return result;
    }

    protected boolean equals(State that) {
        return this.input.equals(that.input);
    }

    public static class Success<InputT extends Input<?>, ResultT>
    extends State<InputT, ResultT> {
        private final ResultT result;

        public ResultT result() { return result; }

        public Success(InputT input, ResultT result) {
            super(input);
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

    public static class Failure<InputT extends Input<?>, ResultT>
    extends State<InputT, ResultT> {
        private final String error;

        public Failure(InputT input, String error) {
            super(input);
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
