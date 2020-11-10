package ujpc.example.mod;

import java.util.List;
import java.util.Collections;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.binary.Binary;
import ujpc.parser.binary.combinator.Ascii;
import ujpc.parser.binary.combinator.UInt8;
import ujpc.parser.binary.combinator.UInt16;
import ujpc.parser.binary.combinator.UInt32;
import ujpc.parser.combinator.Sequence;
import ujpc.parser.combinator.Repeat;

public class ModParser implements Parser<Binary, Mod> {
    private final static int TITLE_LENGTH         =  20;
    private final static int SAMPLES_COUNT        =  31;
    private final static int TYPE_LENGTH          =   4;
    private final static int PATTERN_TABLE_LENGTH = 128;
    private final static int NOTES_COUNT          =   4;
    private final static int PATTERN_ROWS_COUNT   =  64;

    private final Parser<Binary, Mod> parser
        = new Ascii(TITLE_LENGTH).bind(title ->
          new Repeat<Binary, Mod.Sample>(
              SAMPLES_COUNT, new SampleParser()).bind(samples ->
          new UInt8().bind(length ->
          new UInt8().bind(restartPosition ->
          new Repeat<Binary, Integer>(
              PATTERN_TABLE_LENGTH, new UInt8()).bind(patternsTable ->
          new Ascii(TYPE_LENGTH).bind(type ->
          new PatternsParser(patternsTable).map(patterns ->
              new Mod(title, samples, length, patternsTable,
                      type, patterns))))))));

    @Override
    public State<Binary, Mod> parse(Binary in)
        { return parser.parse(in); }

    private static class SampleParser implements Parser<Binary, Mod.Sample> {
        private final static int NAME_LENGTH = 22;

        private final Parser<Binary, Mod.Sample> parser
            = new Ascii(NAME_LENGTH).bind(name ->
              new UInt16().bind(length ->
              new UInt8().bind(finetune ->
              new UInt8().bind(volume ->
              new UInt16().bind(repeatOffset ->
              new UInt16().map(repeatLength ->
                  new Mod.Sample(name, length, finetune, volume,
                                 repeatOffset, repeatLength)))))));

        @Override
        public State<Binary, Mod.Sample> parse(Binary in)
            { return parser.parse(in); }
    }

    private static class PatternsParser
                   implements Parser<Binary, List<Mod.Pattern>> {
        private final Parser<Binary, List<Mod.Pattern>> parser;

        public PatternsParser(List<Integer> patternsTable) {
            this.parser = new Repeat<Binary, Mod.Pattern>(
                Collections.max(patternsTable),
                new PatternParser());
        }

        @Override
        public State<Binary, List<Mod.Pattern>> parse(Binary in)
            { return parser.parse(in); }
    }

    private static class PatternParser implements Parser<Binary, Mod.Pattern> {
        private final Parser<Binary, Mod.Pattern> parser
            = new Repeat<Binary, Mod.Pattern.Row>(
                PATTERN_ROWS_COUNT,
                new RowParser()).map(x -> new Mod.Pattern(x));

        @Override
        public State<Binary, Mod.Pattern> parse(Binary in)
            { return parser.parse(in); }
    }

    private static class RowParser implements Parser<Binary, Mod.Pattern.Row> {
        private final Parser<Binary, Mod.Pattern.Row> parser
            = new Repeat<Binary, Mod.Pattern.Row.Note>(
                NOTES_COUNT, new NoteParser()).map(x -> new Mod.Pattern.Row(x));

        @Override
        public State<Binary, Mod.Pattern.Row> parse(Binary in)
            { return parser.parse(in); }
    }

    private static class NoteParser
                   implements Parser<Binary, Mod.Pattern.Row.Note> {
        private final Parser<Binary, Mod.Pattern.Row.Note> parser
            = new UInt32().map(
                x -> new Mod.Pattern.Row.Note(
                    ((x >>> 24) & 0x000000F0) | ((x >>> 12) & 0x0000000F),
                    (x >>> 16) & 0x00000FFF,
                    x & 0x0000000F));

        @Override
        public State<Binary, Mod.Pattern.Row.Note> parse(Binary in)
            { return parser.parse(in); }
    }
}
