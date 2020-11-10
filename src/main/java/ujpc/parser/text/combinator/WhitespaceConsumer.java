package ujpc.parser.text.combinator;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;
import ujpc.parser.combinator.Sequence;
import static ujpc.util.Lists.first;

public class WhitespaceConsumer<ResultT> implements Parser<Text, ResultT> {
    private Parser<Text, ResultT> parser;

    public WhitespaceConsumer(Parser<Text, ResultT> parser) {
        this.parser = parser;
    }

    @Override
    public State<Text, ResultT> parse(Text in) {
        return new Sequence<Text, ResultT>(
            new Whitespace().map(x -> null),
            parser)
        .map(x -> first(x))
        .parse(in);
    }

    public String toString() { return parser.toString(); }
}
