package ujpc.parser.binary;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import ujpc.parser.Parser;
import ujpc.parser.InputFile;
import ujpc.parser.ParserException;
import ujpc.parser.binary.Binary;

public class BinaryFile<ResultT> extends InputFile<Binary, ResultT> {
    public BinaryFile(Parser<Binary, ResultT> parser, String path) {
        super(parser, path);
    }

    @Override
    protected Binary readFile() throws IOException {
        final byte[] buffer = Files.readAllBytes(Paths.get(path()));
        return new Binary(buffer);
    }
}
