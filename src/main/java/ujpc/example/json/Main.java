package ujpc.example.json;

import java.io.IOException;
import ujpc.parser.text.Text;
import ujpc.parser.ParserException;

public class Main {
    private static final String COMMAND
        = System.getProperty("sun.java.command");

    private static final String USAGE
        = String.format("Usage: java %s INPUT | -f FILE", COMMAND);

    private static final String EXAMPLE
        = String.format("Example: java %s '{\"key\": \"value\"}'\n" +
                        "         java %s -f file.json",
                        COMMAND, COMMAND);

    private static final String ERROR_FORMAT
        = "Error at %s: %s\n%s";

    private static final JsonParser PARSER = new JsonParser();

    public static void main(String[] args) {

        if (args.length == 1) {
            final String string = args[0];
            final Text   input  = new Text(string);

            try {
                final Json result = PARSER.parse(input).getOrThrow();
                System.out.println(result);
            } catch (ParserException e) {
                System.err.println(error(e));
                System.exit(1);
            }

        } else if (args.length == 2
                   && args[0].equals("-f")) {
            final String path = args[1];
            final JsonFile file = new JsonFile(path);

            try {
                final Json result = file.parse();
                System.out.println(result);
            } catch (IOException | ParserException e) {
                System.err.println(e);
                System.exit(1);
            }

        } else {
            System.err.println(USAGE);
            System.err.println(EXAMPLE);
            System.exit(1);
        }
    }

    private static String error(ParserException e) {
        return String.format(ERROR_FORMAT,
                             e.input().position(),
                             e.error(),
                             e.input().needle());
    }
}
