package ujpc.parser;

import ujpc.parser.State.Failure;

public class ParserException extends Exception {
    private final Failure failure;

    public ParserException(Failure failure) {
        this.failure = failure;
    }

    public Input input()  { return failure.input(); }
    public String error() { return failure.error(); }

    @Override
    public String getMessage() {
        return String.format(
            "Error at %s: %s\n" +
            "%s",
            input().position(),
            error(),
            input().needle());
    }
}
