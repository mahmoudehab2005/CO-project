import java.util.HashMap;
import java.util.Map;

public class Parser {
    private String[][] instructions = new String[0][0];
    private Map<String, Integer> labels = new HashMap<>();
    private int[] lineNumbers = new int[0];

    public void parseFile(String filePath) {
        // TODO (Member 1):
        // 1. Read the .txt file (print descriptive error and exit if file not found).
        // 2. Strip ';' comments and blank lines; handle spaces/tabs and comma-separated operands.
        // 3. Record labels (ending with ':') mapped to the index of the next instruction.
        // 4. Populate instructions[][] (uppercase opcode + operands) and original file lineNumbers[].
    }

    public String[][] getInstructions() {
        return instructions;
    }

    public Map<String, Integer> getLabels() {
        return labels;
    }

    public int[] getLineNumbers() {
        return lineNumbers;
    }
}