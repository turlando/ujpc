package ujpc.example.mod;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import ujpc.parser.binary.Binary;


public class ModArchive_181474 {
    private final static ModParser PARSER = new ModParser();

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

    private final static int MOD_PATTERNS_COUNT = 37;

    private final static List<Integer> MOD_PATTERNS_TABLE = List.of(
         0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15, 16,
        17, 18, 19,  8,  9, 20, 21,  6,  7, 26, 24, 22, 23, 27, 28, 29, 32,
        33, 30, 31,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
         0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
         0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
         0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
         0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
         0,  0,  0,  0,  0,  0,  0,  0, 0);

    private final static String MOD_TYPE = "M.K.";

    private final byte[] content;
    private final Mod result;

    public ModArchive_181474() throws java.io.IOException {
        this.content = getClass().getResourceAsStream(FILE_NAME)
                                 .readAllBytes();
        this.result = PARSER.parse(new Binary(content))
                             .match(s -> s.result(),
                                    f -> fail(f.error()));
    }

    @Test void parseTitle() {
        assertEquals(MOD_TITLE, result.title);
    }

    @Test void parseSamples() {
        for (int i = 0; i < 31; i++)
            assertEquals(MOD_SAMPLES.get(i), result.samples.get(i));
    }

    @Test void parsePatternsCount() {
        assertEquals(MOD_PATTERNS_COUNT, result.length);
    }

    @Test void parsePatternsTable() {
        assertEquals(MOD_PATTERNS_TABLE, result.patternsTable);
    }

    @Test void parseType() {
        assertEquals(MOD_TYPE, result.type);
    }
}
