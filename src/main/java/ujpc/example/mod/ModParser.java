package ujpc.example.mod;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.combinator.Sequence;
import ujpc.parser.combinator.Repeat;
import ujpc.parser.combinator.binary.Ascii;
import ujpc.parser.combinator.binary.UInt8;
import ujpc.parser.combinator.binary.UInt16;

public class ModParser implements Parser<byte[], Mod> {
    private final static int TITLE_LENGTH         =  20;
    private final static int SAMPLES_COUNT        =  31;
    private final static int TYPE_LENGTH          =   4;
    private final static int PATTERN_TABLE_LENGTH = 128;

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

    private final Parser<byte[], Mod> parser
        = new Ascii(TITLE_LENGTH).bind(title ->
          new Repeat<byte[], Mod.Sample>(
              SAMPLES_COUNT, new SampleParser()).bind(samples ->
          new UInt8().bind(length ->
          new UInt8().bind(restartPosition ->
          new Repeat<byte[], Integer>(
              PATTERN_TABLE_LENGTH, new UInt8()).bind(patternsTable ->
          new Ascii(TYPE_LENGTH).map(type ->
              new Mod(title, samples, length, patternsTable, type)))))));

    @Override
    public State<byte[], Mod> parse(byte[] in) {
        return parser.parse(in);
    }
}
