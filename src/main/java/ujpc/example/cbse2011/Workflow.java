package ujpc.example.cbse2011;

import java.util.List;

public class Workflow {
    private final List<Statement> statements;

    public Workflow(List<Statement> statements)
        { this.statements = statements; }

    public String toString()
        { return String.format("Workflow(%s)", statements); }

    public static abstract class Statement {
        private Statement() {}

        public static class Sequence extends Statement {
            private final List<String> tasks;

            public Sequence(List<String> tasks)
                { this.tasks = tasks; }

            public String toString()
                { return String.format("Sequence(%s)", tasks); }
        }

        public static class Branch extends Statement {
            private final String    condition;
            private final Statement ifStatement;
            private final Statement elseStatement;

            public Branch(String condition,
                          Statement ifStatement,
                          Statement elseStatement) {
                this.condition = condition;
                this.ifStatement = ifStatement;
                this.elseStatement = elseStatement;
            }

            public String toString() {
                return String.format("Branch(%s, %s, %s)",
                                     condition, ifStatement, elseStatement);
            }
        }

        public static class Loop extends Statement {
            private final String    condition;
            private final Statement statement;

            public Loop(String condition,
                        Statement statement) {
                this.condition = condition;
                this.statement = statement;
            }

            public String toString()
                { return String.format("Loop(%s, %s)", condition, statement); }
        }

        public static class Fork extends Statement {
            private final List<Statement> statements;

            public Fork(List<Statement> statements)
                { this.statements = statements; }

            public String toString()
                { return String.format("Fork(%s)", statements); }
        }
    }
}
