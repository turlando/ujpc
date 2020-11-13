package ujpc.example.mod;

import ujpc.parser.binary.Binary;
import ujpc.parser.binary.BinaryFile;
import ujpc.parser.ParserException;

public class ModFile extends BinaryFile<Mod> {
    public final static ModParser PARSER = new ModParser();

    public ModFile(String path) {
        super(PARSER, path);
    }
}
