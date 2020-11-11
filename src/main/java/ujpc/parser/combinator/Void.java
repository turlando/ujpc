package ujpc.parser.combinator;

import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.Input;

public class Void<InputT extends Input<?>, ResultT>
implements Parser<InputT, ResultT> {
    @Override
    public State<InputT, ResultT> parse(InputT in) {
        return new State.Success<InputT, ResultT>(in, null);
    }

    public String toString() {
        return String.format("Void()");
    }
}
