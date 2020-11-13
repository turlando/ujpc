package ujpc.parser.text;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import ujpc.parser.Parser;
import ujpc.parser.InputFile;
import ujpc.parser.ParserException;
import ujpc.parser.text.Text;

public class TextFile<ResultT> extends InputFile<Text, ResultT> {
    public TextFile(Parser<Text, ResultT> parser, String path) {
        super(parser, path);
    }

    @Override
    protected Text readFile() throws IOException {
        final String buffer = Files.readString(Paths.get(path()));
        return new Text(buffer);
    }
}
