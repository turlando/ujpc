package ujpc.parser.text;

import ujpc.parser.Parser;

public class TextParserTesting {
    private TextParserTesting() {}

    public static <ResultT>
    void canParse(Parser<Text, ResultT> parser, String input,
                  ResultT expectedResult, String expectedLeftover) {
        ujpc.parser.ParserTesting.canParse(parser, new Text(input),
                                           expectedResult, expectedLeftover);
    }

    public static <ResultT>
        void cantParse(Parser<Text, ResultT> parser, String input) {
        ujpc.parser.ParserTesting.cantParse(parser, new Text(input));
    }
}
