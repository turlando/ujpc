package jmod.parser;

public interface Parser<InputT, ResultT> {
    State<InputT, ResultT> parse(State.Success<InputT, ResultT> s);
}
