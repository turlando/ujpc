package jmod.mod;

import java.util.List;

public class Mod {
    public final String       title;
    public final List<Sample> samples;

    public Mod(String title, List<Sample> samples) {
        this.title = title;
        this.samples = samples;
    }

    public static class Builder {
        private final String       title;
        private final List<Sample> samples;

        private Builder(String title, List<Sample> samples) {
            this.title = title;
            this.samples = samples;
        }

        public static Builder ofTitle(String title)
            { return new Builder(title, null); }
        public static Builder ofSamples(List<Sample> samples)
            { return new Builder(null, samples); }

        public static Mod merge(List<Builder> xs) {
            String       title = null;
            List<Sample> samples = null;

            for (Builder x : xs) {
                title   = x.title   == null ? title   : x.title;
                samples = x.samples == null ? samples : x.samples;
            }

            return new Mod(title, samples);
        }
    }

    public static class Sample {
        public final String name;
        public final int    length;
        public final int    finetune;
        public final int    volume;
        public final int    repeatOffset;
        public final int    repeatLength;

        public Sample(String name, int length, int finetune,
                      int volume, int repeatOffset, int repeatLength) {
            this.name = name;
            this.length = length;
            this.finetune = finetune;
            this.volume = volume;
            this.repeatOffset = repeatOffset;
            this.repeatLength = repeatLength;
        }

        public boolean equals(Sample that) {
            return this.name.equals(that.name)
                && this.length == that.length
                && this.finetune == that.finetune
                && this.volume == that.volume
                && this.repeatOffset == that.repeatOffset
                && this.repeatLength == that.repeatLength;
        }

        public boolean equals(Object o)
            { return o instanceof Sample ? equals((Sample) o) : false; }

        public String toString() {
            return String.format("Sample(name=%s, length=%d, finetune=%d, " +
                                 "volume=%d, repeatOffset=%d, repeatLength=%d)",
                                 name, length, finetune, volume,
                                 repeatOffset, repeatLength);
        }

        public static class Builder {
            private final String  name;
            private final Integer length;
            private final Integer finetune;
            private final Integer volume;
            private final Integer repeatOffset;
            private final Integer repeatLength;

            private Builder(String name, Integer length, Integer finetune,
                            Integer volume, Integer repeatOffset, Integer repeatLength) {
                this.name = name;
                this.length = length;
                this.finetune = finetune;
                this.volume = volume;
                this.repeatOffset = repeatOffset;
                this.repeatLength = repeatLength;
            }

            public static Builder ofName(String name)
                { return new Builder(name, null, null, null, null, null); }
            public static Builder ofLength(int length)
                { return new Builder(null, length, null, null, null, null); }
            public static Builder ofFinetune(int finetune)
                { return new Builder(null, null, finetune, null, null, null); }
            public static Builder ofVolume(int volume)
                { return new Builder(null, null, null, volume, null, null); }
            public static Builder ofRepeatOffset(int repeatOffset)
                { return new Builder(null, null, null, null, repeatOffset, null); }
            public static Builder ofRepeatLength(int repeatLength)
                { return new Builder(null, null, null, null, null, repeatLength); }

            public static Sample merge(List<Builder> xs) {
                String name = null;
                Integer length = null;
                Integer finetune = null;
                Integer volume = null;
                Integer repeatOffset = null;
                Integer repeatLength = null;

                for (Builder x : xs) {
                    name         = x.name == null         ? name         : x.name;
                    length       = x.length == null       ? length       : x.length;
                    finetune     = x.finetune == null     ? finetune     : x.finetune;
                    volume       = x.volume == null       ? volume       : x.volume;
                    repeatOffset = x.repeatOffset == null ? repeatOffset : x.repeatOffset;
                    repeatLength = x.repeatLength == null ? repeatLength : x.repeatLength;
                }

                return new Sample(name, length, finetune,
                                  volume, repeatOffset, repeatLength);
            }
        }
    }
}
