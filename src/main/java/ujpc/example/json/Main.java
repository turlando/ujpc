package ujpc.example.json;

public class Main {
    private static final String COMMAND
        = System.getProperty("sun.java.command");

    private static final String USAGE
        = String.format("Usage: java %s INPUT", COMMAND);

    private static final String EXAMPLE
        = String.format("Example: java %s '{\"key\": \"value\"}'", COMMAND);

    private static final JsonParser PARSER = new JsonParser();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(USAGE);
            System.err.println(EXAMPLE);
            System.exit(1);
        }

        final Json result = PARSER.parse(args[0]).getOrThrow();

        System.out.println(result);
    }
}
