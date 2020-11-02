package ujpc.example.mod;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class ModFile {
    public final String  path;
    public final byte[] content;

    public ModFile(String path) throws IOException {
        this.path = path;
        this.content = Files.readAllBytes(Paths.get(path));
    }

    @Override
    public String toString() {
        return String.format("ModFile(%s)", path);
    }
}
