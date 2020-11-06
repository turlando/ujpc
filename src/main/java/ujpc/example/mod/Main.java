package ujpc.example.mod;

public class Main {
    private static final String COMMAND
        = System.getProperty("sun.java.command");

    private static final String USAGE
        = String.format("Usage: java %s PATH", COMMAND);

    private static final String EXAMPLE
        = String.format("Example: java %s banger.mod", COMMAND);

    private static void printInfo(Mod mod) {
        System.out.println("Title: " + mod.title);
        System.out.println("Samples:");

        for (Mod.Sample sample : mod.samples)
            System.out.println("  - " + sample);

        System.out.println("Patterns: " + mod.patternsCount);
        System.out.println("Type: " + mod.type);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(USAGE);
            System.err.println(EXAMPLE);
            System.exit(1);
        }

        String path = args[0];
        ModFile file = null;

        try {
            file = new ModFile(path);
        } catch (java.io.IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        printInfo(file.mod);
    }
}
