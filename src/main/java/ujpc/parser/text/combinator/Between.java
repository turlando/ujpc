package ujpc.parser.text.combinator;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;
import ujpc.parser.combinator.Sequence;

public class Between<ResultT> implements Parser<Text, ResultT> {
    private final Parser<Text, String> leftParser;
    private final Parser<Text, String> rightParser;
    private final Parser<Text, ResultT> target;
    private final Parser<Text, ResultT> parser;

    public Between(Parser<Text, String> leftParser,
                   Parser<Text, String> rightParser,
                   Parser<Text, ResultT> target) {
        this.leftParser = leftParser;
        this.rightParser = rightParser;
        this.target = target;
        this.parser = new Sequence<Text, ResultT>(
            leftParser.map(x -> null),
            target,
            rightParser.map(x -> null))
            .map(xs -> xs.get(0));
    }

    @Override
    public State<Text, ResultT> parse(Text in) {
        return parser.parse(in);
    }

    public String toString() {
        return String.format("Between(%s, %s, %s)",
                             leftParser, rightParser, target);
    }
}
