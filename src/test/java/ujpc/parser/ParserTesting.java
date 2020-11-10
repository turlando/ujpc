package ujpc.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTesting {
    private ParserTesting() {}

    public static <InputV, InputT extends Input<InputV>, ResultT>
    void canParse(Parser<InputT, ResultT> parser, InputT input,
                  ResultT expectedResult, InputV expectedLeftover) {
        final State<InputT, ResultT> state    = parser.parse(input);
        assertEquals(expectedLeftover, state.input().rest());

        try   { assertEquals(expectedResult, state.getOrThrow()); }
        catch (ParserException e) { fail(e); }
    }

    public static <InputT extends Input<?>, ResultT>
    void cantParse(Parser<InputT, ResultT> parser, InputT input) {
        assertEquals(State.Failure.class, parser.parse(input).getClass());
    }
}
