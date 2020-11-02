package ujpc.example.mod;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import ujpc.parser.State;

public class ModArchive_181474 {
    private final static String FILE_NAME
        = "/ujpc/example/mod/" +
          "ModArchive.181474 - DJ STAX - Can You Feel It (Remix).mod";

    private final static String MOD_TITLE =  "Can U Fell It(Remix)";

    private final static List<Mod.Sample> MOD_SAMPLES = List.of(
        new Mod.Sample("**********************",  7500, 15, 64, 0, 7500),
        new Mod.Sample("****    DJ STAX   ****", 18336, 15, 64, 0,    1),
        new Mod.Sample("****   PRESENTS  *****",  2282, 15, 64, 0,    1),
        new Mod.Sample("****Can U Feel It ****",  1376, 15, 64, 0,    1),
        new Mod.Sample("****     1993     ****",   936, 15, 64, 0,    1),
        new Mod.Sample("**********************",   525, 15, 64, 0,    1),
        new Mod.Sample("Hello Everybody",         1413, 15, 64, 0,    1),
        new Mod.Sample("This is an another mod",  1345,  3, 64, 0,    1),
        new Mod.Sample("from my head",            1351,  3, 64, 0,    1),
        new Mod.Sample("It's fantastic mod",      1353,  3, 64, 0,    1),
        new Mod.Sample("I like this",             1343,  3, 64, 0,    1),
        new Mod.Sample("143 BPM",                 2676, 15, 64, 0,    1),
        new Mod.Sample("limit jungle song",      20930, 15, 64, 0,    1),
        new Mod.Sample("Big thanks for",          3744,  1, 64, 0,    1),
        new Mod.Sample("my friend : yohann",      1296,  1, 64, 0,    1),
        new Mod.Sample("Bye By DJ STAX",          3832,  1, 64, 0,    1),
        new Mod.Sample("**********************", 15968, 15, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1),
        new Mod.Sample("",                           0,  0, 64, 0,    1));

    private final int MOD_PATTERNS_COUNT = 37;

    private final ModParser PARSER = new ModParser();

    private final byte[] content;
    private final State.Success<byte[], Mod> initial;
    private final Mod result;

    public ModArchive_181474() throws java.io.IOException {
        this.content = this.getClass().getResourceAsStream(FILE_NAME)
                                      .readAllBytes();
        this.initial = new State.Success<byte[], Mod>(content, null);
        this.result = PARSER.parse(initial).match(s -> s.result, f -> null);
    }

    @Test void parseTitle() {
        assertEquals(MOD_TITLE, result.title);
    }

    @Test void parseSamples() {
        for (int i = 0; i < 31; i++)
            assertEquals(MOD_SAMPLES.get(i), result.samples.get(i));
    }

    @Test void parsePatternsCount() {
        assertEquals(MOD_PATTERNS_COUNT, result.patternsCount);
    }
}
