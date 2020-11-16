package ujpc.example.cbse2011;

import ujpc.parser.text.Text;
import ujpc.parser.text.TextFile;

public class WorkflowFile extends TextFile<Workflow> {
    private final static WorkflowParser PARSER = new WorkflowParser();

    public WorkflowFile(String path) {
        super(PARSER, path);
    }
}
