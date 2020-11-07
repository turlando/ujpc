package ujpc.example.mod;

import java.util.List;

public class Mod {
    public final String        title;
    public final List<Sample>  samples;
    public final int           length;
    public final List<Integer> patternsTable;
    public final String        type;
    public final List<Pattern> patterns;

    public Mod(String title, List<Sample> samples, int length,
               List<Integer> patternsTable, String type,
               List<Pattern> patterns) {
        this.title         = title;
        this.samples       = samples;
        this.length = length;
        this.patternsTable = patternsTable;
        this.type          = type;
        this.patterns      = patterns;
    }

    public boolean equals(Mod that) {
        return this.title.equals(that.title)
            && this.samples.equals(that.samples)
            && this.length == that.length
            && this.patternsTable.equals(that.patternsTable)
            && this.type.equals(that.type)
            && this.patterns.equals(that.patterns);
    }

    public boolean equals(Object o)
        { return o instanceof Mod ? equals((Mod) o) : false; }

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
    }

    public static class Pattern {
        public final List<Row> rows;

        public Pattern(List<Row> rows) {
            this.rows = rows;
        }

        public boolean equals(Pattern that) {
            return this.rows.equals(that.rows);
        }

        public boolean equals(Object o)
            { return o instanceof Sample ? equals((Pattern) o) : false; }

        public static class Row {
            public final List<Note> notes;

            public Row(List<Note> notes) {
                this.notes = notes;
            }

            public boolean equals(Row that)
                { return this.notes.equals(that.notes); }

            public boolean equals(Object o)
                { return o instanceof Row ? equals((Row) o) : false; }

            public static class Note {
                public final int sample;
                public final int period;
                public final int effect;

                public Note(int sample, int period, int effect) {
                    this.sample = sample;
                    this.period = period;
                    this.effect = effect;
                }

                public boolean equals(Note that) {
                    return this.sample == that.sample
                        && this.period == that.period
                        && this.effect == that.effect;
                }

                public boolean equals(Object o)
                    { return o instanceof Note ? equals((Note) o) : false; }

                public String toString() {
                    return String.format("Note(sample=%d, period=%d, effect=%d)",
                                         sample, period, effect);
                }
            }

        }
    }
}
