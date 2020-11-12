package ujpc.example.mod;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import ujpc.parser.InputFile;
import ujpc.parser.binary.Binary;
import ujpc.parser.ParserException;

public class ModFile extends InputFile<Binary, Mod> {
    public final static ModParser PARSER = new ModParser();

    public ModFile(String path) {
        super(PARSER, path);
    }

    @Override
    protected Binary readFile() throws IOException {
        final byte[] buffer = Files.readAllBytes(Paths.get(path));
        return new Binary(buffer);
    }
}
