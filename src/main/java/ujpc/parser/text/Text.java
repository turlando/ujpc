package ujpc.parser.text;

import java.util.List;
import java.util.Arrays;
import ujpc.parser.Input;
import ujpc.util.Strings;
import static ujpc.util.Lists.append;
import static ujpc.util.Lists.concat;
import static ujpc.util.Lists.last;

public class Text
extends Input<String> {
    private final List<Line> lines;
    private final int lineOffset;
    private final int columnOffset;

    private Text(String input, int offset,
                 List<Line> lines, int lineOffset, int columnOffset) {
        super(input, offset);
        this.lines = lines;
        this.lineOffset = lineOffset;
        this.columnOffset = columnOffset;
    }

    public Text(String input) { this(input, 0, stringToLines(input), 0, 0); }

    public int line()   { return lineOffset + 1; }    // 1-based indexing for
    public int column() { return columnOffset + 1; }  // human-friendliness

    @Override public String rest()      { return Strings.drop(input(), offset()); }
    @Override public String take(int n) { return Strings.take(rest(), n); }
    @Override public String drop(int n) { return Strings.drop(rest(), n); }

    @Override
    public Text addOffset(int offset) {
        final String substring = take(offset);
        final int newlines = Strings.newlines(substring);
        final int newOffset = offset() + offset;
        final int newLineOffset = lineOffset + newlines;
        final int newColumnOffset
            // Update column as the difference between offset and start of line.
            = newOffset - lines.get(newLineOffset).offset();

        return new Text(
            input(), newOffset,  // input pass-thru, offset update
            lines,               // lines pass-thru
            newLineOffset,       // line number update
            newColumnOffset);    // column number update
    }

    @Override
    public String position() {
        return String.format("%d:%d", line(), column());
    }

    @Override
    public String needle() {
        final int lineNumberLength = line() > 1
                                     ? (int) Math.ceil(Math.log10((int) line()))
                                     : 1;

        return String.format(" %" + lineNumberLength + "d | %s\n" +
                             " %" + lineNumberLength + "s | %" + column() + "s",
                             line(), lines.get(lineOffset).line(),
                             " ", "^");
    }

    private static List<Line> stringToLines(String s) {
        return s.lines()
            .reduce(List.<Line> of(),
                    (acc, x) -> append(
                        acc,
                        new Line(x,
                                 x.length() + 1, // + 1 required because lines()
                                                 // strips trailing \n
                                 acc.isEmpty()
                                 ? 0
                                 : last(acc).offset()
                                   + last(acc).length())),
                    (x, y) -> concat(x, y));
    }

    private static class Line {
        private String line;
        private int length;
        private int offset;

        public Line(String line, int length, int offset) {
            this.line = line;
            this.length = length;
            this.offset = offset;
        }

        public String line()   { return line; }
        public int    length() { return length; }
        public int    offset() { return offset; }
    }
}
