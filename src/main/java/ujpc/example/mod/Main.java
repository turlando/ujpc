package ujpc.example.mod;

import ujpc.parser.ParserException;

public class Main {
    private static final String COMMAND
        = System.getProperty("sun.java.command");

    private static final String USAGE
        = String.format("Usage: java %s PATH", COMMAND);

    private static final String EXAMPLE
        = String.format("Example: java %s banger.mod", COMMAND);

    private static void printInfo(Mod mod) {
        System.out.println("Title: " + mod.title);
        System.out.println("Type: " + mod.type);
        System.out.println();

        System.out.println("Samples:");
        for (Mod.Sample sample : mod.samples)
            System.out.println(String.format(
                "    - Name=%-22s, Length=%5d, Finetune=%3d, Volume=%3d, " +
                "RepeatOffset=%4d, RepeatLength=%4d",
                sample.name, sample.length, sample.finetune, sample.volume,
                sample.repeatOffset, sample.repeatLength));
        System.out.println();

        System.out.println("Pattern table: " + mod.patternsTable);
        System.out.println();

        System.out.println("Patterns:");
        for (int i = 0; i < mod.patterns.size(); i++) {
            Mod.Pattern pattern = mod.patterns.get(i);
            System.out.println("    - Pattern " + (i + 1));

            for (Mod.Pattern.Row row : pattern.rows) {
                System.out.print("        - ");
                for (Mod.Pattern.Row.Note note : row.notes)
                    System.out.print(String.format(
                        "Sample=%3d, Period=%4d, Effect=%4d | ",
                        note.sample, note.period, note.effect));
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(USAGE);
            System.err.println(EXAMPLE);
            System.exit(1);
        }

        String path = args[0];
        ModFile file = new ModFile(path);

        try {
            final Mod result = file.parse();
            printInfo(result);
        } catch (java.io.IOException e) {
            System.err.println(e);
            System.exit(1);
        } catch (ParserException e) {
            System.err.println(e.error());
            System.exit(1);
        }
    }
}
