package jmod.parser.string;

import jmod.parser.Input;

public class StringInput implements Input<String> {
    private String input;

    public StringInput(String input) {
        this.input = input;
    }

    @Override
    public String get() {
        return input;
    }

    @Override
    public StringInput take(int n) {
        final int end = Math.min(n, input.length());
        return new StringInput(input.substring(0, end));
    }

    @Override
    public StringInput skip(int n) {
        if (n > input.length())
            return new StringInput("");
        return new StringInput(input.substring(n - 1, input.length() - 1));
    }

    @Override
    public boolean eof() {
        return input.isEmpty();
    }

    public boolean equals(StringInput that) {
        return this.input.equals(that.input);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof StringInput)
            return equals((StringInput) that);
        return false;
    }

    @Override
    public String toString() {
        return String.format("StringInput(%s)", input);
    }
}
