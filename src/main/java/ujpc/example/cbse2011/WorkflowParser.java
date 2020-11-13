package ujpc.example.cbse2011;

import java.util.List;
import ujpc.parser.Parser;
import ujpc.parser.State;
import ujpc.parser.text.Text;
import ujpc.parser.combinator.Sequence;
import ujpc.parser.combinator.Choice;
import ujpc.parser.text.combinator.Alphanum;
import ujpc.parser.text.combinator.Token;
import ujpc.parser.text.combinator.Separated;
import ujpc.parser.text.combinator.WhitespaceConsumer;

public class WorkflowParser implements Parser<Text, Workflow> {
    private final Token begin     = new Token("begin");
    private final Token end       = new Token("end");
    private final Token if_       = new Token("if");
    private final Token then      = new Token("then");
    private final Token else_     = new Token("else");
    private final Token endif     = new Token("endif");
    private final Token while_    = new Token("while");
    private final Token do_       = new Token("do");
    private final Token endwhile  = new Token("endwhile");
    private final Token fork      = new Token("fork");
    private final Token join      = new Token("join");
    private final Token semicolon = new Token(";");
    private final Token pipes     = new Token("||");

    private final Alphanum identifier = new Alphanum();

    private class StatementParser implements Parser<Text, Workflow.Statement> {
        @Override
        public State<Text, Workflow.Statement> parse(Text in) {
            return new Choice<Text, Workflow.Statement>(
                new BranchParser(),
                new LoopParser(),
                new ForkParser(),
                new SequenceParser()
            ).parse(in);
        }
    }

    private class SequenceParser implements Parser<Text, Workflow.Statement> {
        private final Parser<Text, Workflow.Statement> parser
            = new Separated<>(
                  semicolon,
                  new WhitespaceConsumer<>(identifier)
              ).map(x -> new Workflow.Statement.Sequence(x));

        @Override
        public State<Text, Workflow.Statement> parse(Text in) {
            return parser.parse(in);
        }
    }

    private class BranchParser implements Parser<Text, Workflow.Statement> {
        @Override
        public State<Text, Workflow.Statement> parse(Text in) {
            final Parser<Text, Workflow.Statement> parser
                = new WhitespaceConsumer<>(if_).bind(_if ->
                  new WhitespaceConsumer<>(identifier).bind(condition ->
                  new WhitespaceConsumer<>(then).bind(_then ->
                  new WhitespaceConsumer<>(new StatementParser()).bind(ifStm->
                  new WhitespaceConsumer<>(else_).bind(_else ->
                  new WhitespaceConsumer<>(new StatementParser()).bind(elseStm ->
                  new WhitespaceConsumer<>(endif).map(_endif ->
                    new Workflow.Statement.Branch(
                        condition, ifStm, elseStm))))))));
                return parser.parse(in);
        }
    }

    private class LoopParser implements Parser<Text, Workflow.Statement> {
        @Override
        public State<Text, Workflow.Statement> parse(Text in) {
            final Parser<Text, Workflow.Statement> parser
                = new WhitespaceConsumer<>(while_).bind(_while ->
                  new WhitespaceConsumer<>(identifier).bind(condition ->
                  new WhitespaceConsumer<>(do_).bind(_do ->
                  new WhitespaceConsumer<>(new StatementParser()).bind(stm ->
                  new WhitespaceConsumer<>(endwhile).map(_endwhile ->
                  new Workflow.Statement.Loop(condition, stm))))));
            return parser.parse(in);
        }
    }

    private class ForkParser implements Parser<Text, Workflow.Statement> {
        @Override
        public State<Text, Workflow.Statement> parse(Text in) {
            final Parser<Text, List<Workflow.Statement>> forklist
                = new Separated<>(
                      new WhitespaceConsumer<>(pipes),
                      new WhitespaceConsumer<>(new StatementParser()));
            final Parser<Text, Workflow.Statement> parser
                = new WhitespaceConsumer<>(fork).bind(_fork ->
                  new WhitespaceConsumer<>(forklist).bind(stm ->
                  new WhitespaceConsumer<>(join).map(_join ->
                  new Workflow.Statement.Fork(stm))));
            return parser.parse(in);
        }
    }

    private final Parser<Text, Workflow> parser
        = new Sequence<>(
              new WhitespaceConsumer<>(begin).map(x -> null),
              new WhitespaceConsumer<>(new StatementParser()),
              new WhitespaceConsumer<>(end).map(x -> null)
          ).map(x -> new Workflow(x));

    @Override
    public State<Text, Workflow> parse(Text in) {
        return parser.parse(in);
    }
}
