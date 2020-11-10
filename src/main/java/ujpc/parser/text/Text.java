package ujpc.parser.text;

import java.util.List;
import java.util.Arrays;
import ujpc.parser.Input;
import static ujpc.util.Strings.drop;
import static ujpc.util.Strings.take;
import static ujpc.util.Strings.newlines;
import static ujpc.util.Lists.append;
import static ujpc.util.Lists.concat;
import static ujpc.util.Lists.last;

public class Text
extends Input<String> {
    private final List<Line> lines;
    private final int line;
    private final int column;

    private Text(String input, int offset,
                 List<Line> lines, int line, int column) {
        super(input, offset);
        this.lines = lines;
        this.line = line;
        this.column = column;
    }

    public Text(String input) { this(input, 0, stringToLines(input), 0, 0); }

    public int line()   { return line; }
    public int column() { return column; }

    @Override public String rest() { return drop(input, offset); }

    @Override
    public Text addOffset(int offset) {
        final String substring = take(rest(), offset);
        final int newlines = newlines(substring);
        final int newOffset = this.offset + offset;
        final int newLineNumber = line + newlines;
        final int newColumnNumber
            // update column as the difference between offset and start of line
            = newOffset - lines.get(newLineNumber).offset();

        return new Text(
            input, newOffset,  // input pass-thru, offset update
            lines,             // lines pass-thru
            newLineNumber,     // line number update
            newColumnNumber);  // column number update
    }

    @Override
    public String position() {
        return String.format("%d:%d", line(), column());
    }

    @Override
    public String needle() {
        final int lineNumberLength = line > 0
                                     ? (int) Math.ceil(Math.log10((int) line))
                                     : 1;
        return String.format(" %" + lineNumberLength + "d | %s\n" +
                             " %" + lineNumberLength + "s | %" + column + "s",
                             line, lines.get(line).line(),
                             " ", "^");
    }

    private static List<Line> stringToLines(String s) {
        return Arrays.stream(s.split("\n"))
            .reduce(List.<Line> of(),
                    (acc, x) -> append(acc, new Line(x, x.length(),
                                                     acc.isEmpty()
                                                     ? 0
                                                     : last(acc).offset()
                                                       + x.length())),
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
