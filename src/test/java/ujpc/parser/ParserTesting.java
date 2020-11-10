package ujpc.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTesting {
    private ParserTesting() {}

    public static <InputV, InputT extends Input<InputV>, ResultT>
    void canParse(Parser<InputT, ResultT> parser, InputT input,
                  ResultT expectedResult, InputV expectedLeftover) {
        final State<InputT, ResultT> state    = parser.parse(input);
        final ResultT                result   = state.getOrThrow();
        final InputV                 leftover = state.input().rest();

        assertEquals(expectedResult, result);
        assertEquals(expectedLeftover, leftover);
    }

    public static <InputT extends Input<?>, ResultT>
    void cantParse(Parser<InputT, ResultT> parser, InputT input) {
        assertEquals(State.Failure.class, parser.parse(input).getClass());
    }
}
