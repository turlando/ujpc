package ujpc.example.mod;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class ModFile {
    public final static ModParser PARSER = new ModParser();

    public final String path;
    public final byte[] bytes;
    public final Mod    mod;

    public ModFile(String path) throws IOException {
        this.path  = path;
        this.bytes = Files.readAllBytes(Paths.get(path));
        this.mod   = PARSER.parse(bytes).match(s -> s.result, f -> null);
    }

    @Override
    public String toString() {
        return String.format("ModFile(%s)", path);
    }
}
