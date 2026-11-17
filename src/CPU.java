import java.util.Map;

public class CPU {
    private Registers registers;
    private Memory memory;
    private ALU alu;
    private String[][] instructions;
    private Map<String, Integer> labels;
    private int[] lineNumbers;
    private int pc = 0;

    public CPU(Registers registers, Memory memory, ALU alu,
               String[][] instructions, Map<String, Integer> labels, int[] lineNumbers) {
        this.registers = registers;
        this.memory = memory;
        this.alu = alu;
        this.instructions = instructions;
        this.labels = labels;
        this.lineNumbers = lineNumbers;
    }

    public void run() {
        // TODO (Member 3):
        // 1. Fetch-decode-execute loop while pc < instructions.length.
        // 2. Delegate compute opcodes (ADD, SUB, MUL, DIV, AND, OR, XOR, NOT, SHL, SHR, CMP) to alu.execute().
        // 3. Implement MOV, LOAD, STORE, JMP, JZ, JN, JNZ, PRINT, and HALT.
        // 4. Report unknown opcodes and stop; print warning if loop finishes without HALT.
        // 5. Print the formatted execution trace for each executed instruction.
    }
}