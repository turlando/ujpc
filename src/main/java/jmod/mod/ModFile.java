package jmod.mod;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class ModFile {
    public final String     path;
    public final ByteBuffer content;

    public ModFile(String path) throws IOException {
        this.path = path;
        this.content = ByteBuffer.wrap(Files.readAllBytes(Paths.get(path)))
                                 .asReadOnlyBuffer();
    }

    @Override
    public String toString() {
        return String.format("ModFile(%s)", path);
    }
}
