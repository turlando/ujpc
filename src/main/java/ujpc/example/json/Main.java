package ujpc.example.json;

import ujpc.parser.ParserException;
import ujpc.parser.text.Text;

public class Main {
    private static final String COMMAND
        = System.getProperty("sun.java.command");

    private static final String USAGE
        = String.format("Usage: java %s INPUT", COMMAND);

    private static final String EXAMPLE
        = String.format("Example: java %s '{\"key\": \"value\"}'", COMMAND);

    private static final String ERROR_FORMAT
        = "Error at %s: %s\n%s";

    private static final JsonParser PARSER = new JsonParser();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(USAGE);
            System.err.println(EXAMPLE);
            System.exit(1);
        }

        Json result = null;

        try {
            result = PARSER.parse(new Text(args[0])).getOrThrow();
        } catch (ParserException e) {
            System.err.println(
                String.format(ERROR_FORMAT,
                              e.input().position(),
                              e.error(),
                              e.input().needle()));
            System.exit(1);
        }

        System.out.println(result);
    }
}
