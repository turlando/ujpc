package ujpc.example.mod;

public class Main {
    public static void main(String[] args) {
        ModFile file = null;

        try {
            file = new ModFile(args[0]);
        } catch (java.io.IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        System.out.println("Title: " + file.mod.title);
        System.out.println("Samples:");

        for (Mod.Sample sample : file.mod.samples)
            System.out.println("  - " + sample);
    }
}
