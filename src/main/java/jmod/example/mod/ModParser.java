package jmod.example.mod;

import jmod.parser.Parser;
import jmod.parser.State;
import jmod.parser.combinator.Sequence;
import jmod.parser.combinator.Repeat;
import jmod.parser.combinator.binary.Ascii;
import jmod.parser.combinator.binary.UInt8;
import jmod.parser.combinator.binary.UInt16;

public class ModParser implements Parser<byte[], Mod> {
    private final static int TITLE_LENGTH = 20;
    private final static int SAMPLES_COUNT = 31;

    private static class SampleParser implements Parser<byte[], Mod.Sample> {
        private final static int NAME_LENGTH = 22;

        private final Parser<byte[], Mod.Sample> parser
            = new Sequence<byte[], Mod.Sample.Builder>(
                new Ascii(NAME_LENGTH).map(x -> Mod.Sample.Builder.ofName(x)),
                new UInt16().map(x -> Mod.Sample.Builder.ofLength(x)),
                new UInt8().map(x -> Mod.Sample.Builder.ofFinetune(x)),
                new UInt8().map(x -> Mod.Sample.Builder.ofVolume(x)),
                new UInt16().map(x -> Mod.Sample.Builder.ofRepeatOffset(x)),
                new UInt16().map(x -> Mod.Sample.Builder.ofRepeatLength(x)))
              .map(x -> Mod.Sample.Builder.merge(x));

        @Override
        public State<byte[], Mod.Sample>
        parse(State.Success<byte[], Mod.Sample> s) {
            return parser.parse(s);
        }
    }

    private final Parser<byte[], Mod> parser
        = new Sequence<byte[], Mod.Builder>(
            new Ascii(TITLE_LENGTH).map(x -> Mod.Builder.ofTitle(x)),
            new Repeat<byte[], Mod.Sample>(
                SAMPLES_COUNT,
                new SampleParser()).map(x-> Mod.Builder.ofSamples(x)))
          .map(x -> Mod.Builder.merge(x));

    @Override
    public State<byte[], Mod> parse(State.Success<byte[], Mod> s) {
        return parser.parse(s);
    }
}