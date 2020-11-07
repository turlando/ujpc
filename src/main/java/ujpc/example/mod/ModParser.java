package ujpc.example.mod;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.combinator.Sequence;
import ujpc.parser.combinator.Repeat;
import ujpc.parser.combinator.binary.Ascii;
import ujpc.parser.combinator.binary.UInt8;
import ujpc.parser.combinator.binary.UInt16;
import ujpc.parser.combinator.binary.UInt32;

import java.util.List;
import java.util.Collections;

public class ModParser implements Parser<byte[], Mod> {
    private final static int TITLE_LENGTH         =  20;
    private final static int SAMPLES_COUNT        =  31;
    private final static int TYPE_LENGTH          =   4;
    private final static int PATTERN_TABLE_LENGTH = 128;
    private final static int NOTES_COUNT          =   4;
    private final static int PATTERN_ROWS_COUNT   =  64;

    private static class SampleParser implements Parser<byte[], Mod.Sample> {
        private final static int NAME_LENGTH = 22;

        private final Parser<byte[], Mod.Sample> parser
            = new Ascii(NAME_LENGTH).bind(name ->
              new UInt16().bind(length ->
              new UInt8().bind(finetune ->
              new UInt8().bind(volume ->
              new UInt16().bind(repeatOffset ->
              new UInt16().map(repeatLength ->
                  new Mod.Sample(name, length, finetune, volume,
                                 repeatOffset, repeatLength)))))));

        @Override
        public State<byte[], Mod.Sample> parse(byte[] in) {
            return parser.parse(in);
        }
    }

    private static class NoteParser
                   implements Parser<byte[], Mod.Pattern.Row.Note> {
        private final Parser<byte[], Mod.Pattern.Row.Note> parser
            = new UInt32().map(
                x -> new Mod.Pattern.Row.Note(
                    ((x >>> 24) & 0x000000F0) | ((x >>> 12) & 0x0000000F),
                    (x >>> 16) & 0x00000FFF,
                    x & 0x0000000F));

        @Override
        public State<byte[], Mod.Pattern.Row.Note> parse(byte[] in) {
            return parser.parse(in);
        }
    }

    private static class RowParser implements Parser<byte[], Mod.Pattern.Row> {
        private final Parser<byte[], Mod.Pattern.Row> parser
            = new Repeat<byte[], Mod.Pattern.Row.Note>(
                NOTES_COUNT, new NoteParser()).map(x -> new Mod.Pattern.Row(x));

        @Override
        public State<byte[], Mod.Pattern.Row> parse(byte[] in) {
            return parser.parse(in);
        }
    }

    private static class PatternParser implements Parser<byte[], Mod.Pattern> {
        private final Parser<byte[], Mod.Pattern> parser
            = new Repeat<byte[], Mod.Pattern.Row>(
                PATTERN_ROWS_COUNT,
                new RowParser()).map(x -> new Mod.Pattern(x));

        @Override
        public State<byte[], Mod.Pattern> parse(byte[] in) {
            return parser.parse(in);
        }
    }

    private static class PatternList implements Parser<byte[], List<Mod.Pattern>> {
        private final int length;
        private final Parser<byte[], List<Mod.Pattern>> parser;

        public PatternList(List<Integer> patternsTable) {
            this.length = Collections.max(patternsTable);
            this.parser = new Repeat<byte[], Mod.Pattern>(
                length, new PatternParser());
        }

        @Override
        public State<byte[], List<Mod.Pattern>> parse(byte[] in) {
            return parser.parse(in);
        }
    }

    private final Parser<byte[], Mod> parser
        = new Ascii(TITLE_LENGTH).bind(title ->
          new Repeat<byte[], Mod.Sample>(
              SAMPLES_COUNT, new SampleParser()).bind(samples ->
          new UInt8().bind(length ->
          new UInt8().bind(restartPosition ->
          new Repeat<byte[], Integer>(
              PATTERN_TABLE_LENGTH, new UInt8()).bind(patternsTable ->
          new Ascii(TYPE_LENGTH).bind(type ->
          new PatternList(patternsTable).map(patterns ->
              new Mod(title, samples, length, patternsTable,
                      type, patterns))))))));

    @Override
    public State<byte[], Mod> parse(byte[] in) {
        return parser.parse(in);
    }
}
