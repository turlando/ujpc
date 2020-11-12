package ujpc.example.json;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import ujpc.parser.InputFile;
import ujpc.parser.text.Text;
import ujpc.parser.ParserException;

public class JsonFile extends InputFile<Text, Json> {
    public final static JsonParser PARSER = new JsonParser();

    public JsonFile(String path) {
        super(PARSER, path);
    }

    @Override
    protected Text readFile() throws IOException {
        final String buffer = Files.readString(Paths.get(path));
        return new Text(buffer);
    }
}
