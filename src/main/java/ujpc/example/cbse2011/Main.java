package ujpc.example.cbse2011;

import java.io.IOException;
import ujpc.parser.ParserException;
import ujpc.parser.text.TextFile;

public class Main {
    private static final String COMMAND
        = System.getProperty("sun.java.command");

    private static final String USAGE
        = String.format("Usage: java %s PATH", COMMAND);

    private static final String EXAMPLE
        = String.format("Example: java %s workflow.txt", COMMAND);

    private static final WorkflowParser PARSER = new WorkflowParser();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(USAGE);
            System.err.println(EXAMPLE);
            System.exit(1);
        }

        final String path = args[0];

        try {
            Workflow result = new TextFile<>(PARSER, path).parse();
            System.out.println(result);
        } catch (java.io.IOException | ParserException e) {
            System.err.println(e);
            System.exit(1);
        }
    }
}
