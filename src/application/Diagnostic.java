package application;
import java.util.List;

public class Diagnostic {
    private String problem;
    private List<String> causes;

    public Diagnostic(String problem, List<String> causes) {
        this.problem = problem;
        this.causes = causes;
    }

    public String getProblem() {
        return problem;
    }

    public List<String> getCauses() {
        return causes;
    }
}