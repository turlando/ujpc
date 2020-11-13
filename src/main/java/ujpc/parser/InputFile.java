package ujpc.parser;

import java.io.IOException;

public abstract class InputFile<InputT extends Input<?>, ResultT> {
    private final Parser<InputT, ResultT> parser;
    private final String                  path;

    protected InputFile(Parser<InputT, ResultT> parser, String path) {
        this.parser = parser;
        this.path   = path;
    }

    protected Parser<InputT, ResultT> parser() { return parser; }
    protected String                  path()   { return path; }

    protected abstract InputT readFile() throws IOException;

    public ResultT parse() throws IOException, ParserException {
        final InputT input = readFile();
        return parser.parse(input).getOrThrow();
    }
}
