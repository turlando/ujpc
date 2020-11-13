package ujpc.example.json;

import ujpc.parser.text.Text;
import ujpc.parser.text.TextFile;

    public class JsonFile extends TextFile<Json> {
    public final static JsonParser PARSER = new JsonParser();

    public JsonFile(String path) {
        super(PARSER, path);
    }
}
