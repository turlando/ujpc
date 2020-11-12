package ujpc.parser;

import java.io.IOException;

public abstract class InputFile<InputT extends Input<?>, ResultT> {
    protected final Parser<InputT, ResultT> parser;
    protected final String                  path;

    protected InputFile(Parser<InputT, ResultT> parser, String path) {
        this.parser = parser;
        this.path   = path;
    }

    protected abstract InputT readFile() throws IOException;

    public ResultT parse() throws IOException, ParserException {
        final InputT input = readFile();
        return parser.parse(input).getOrThrow();
    }
}
