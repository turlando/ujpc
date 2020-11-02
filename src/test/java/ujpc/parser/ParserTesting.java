package ujpc.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTesting {
    private ParserTesting() {}

    public static <InputT, ResultT> void canParse(
        Parser<InputT, ResultT> parser, InputT input,
        ResultT expected, InputT leftover)
    {
        final State.Success<InputT, ResultT> expectedState
            = new State.Success<>(leftover, expected);
        assertEquals(expectedState, parser.parse(input));
    }

    public static <InputT, ResultT> void cantParse(
        Parser<InputT, ResultT> parser, InputT input
    ) {
        assertEquals(State.Failure.class, parser.parse(input).getClass());
    }
}
