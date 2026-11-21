import java.util.Map;

public class CPU {
    private Registers registers;
    private Memory memory;
    private ALU alu;
    private String[][] instructions;
    private Map<String, Integer> labels;
    private int[] lineNumbers;
    private int pc = 0;

    private static final String[] ALU_OPCODES = {
        "ADD", "SUB", "MUL", "DIV", "AND", "OR", "XOR",
        "NOT", "SHL", "SHR", "CMP"
    };

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
        while (pc >= 0 && pc < instructions.length) {
            String[] instruction = instructions[pc];
            if (instruction == null || instruction.length == 0) {
                pc++;
                continue;
            }

            String opcode = instruction[0].trim().toUpperCase();
            String[] operands = new String[instruction.length - 1];
            System.arraycopy(instruction, 1, operands, 0, operands.length);

            int line = getLineNumber(pc);
            int nextPc = pc + 1;

            if (isAluOpcode(opcode)) {
                alu.execute(opcode, operands, line);
                printTrace(line, instruction);
                pc = nextPc;
                continue;
            }

            switch (opcode) {
                case "MOV":
                    requireOperands(opcode, operands, 2, line);
                    {
                        int value = registers.getValue(operands[1], line);
                        registers.set(operands[0], value, line);
                        printTrace(line, instruction, operands[0].toUpperCase() + " = " + value);
                    }
                    break;
                case "LOAD":
                    requireOperands(opcode, operands, 2, line);
                    {
                        int address = registers.getValue(operands[1], line);
                        int value = memory.read(address, line);
                        registers.set(operands[0], value, line);
                        printTrace(line, instruction, operands[0].toUpperCase()
                            + " = " + value + ", Memory[" + address + "] = " + value);
                    }
                    break;
                case "STORE":
                    requireOperands(opcode, operands, 2, line);
                    {
                        int address = registers.getValue(operands[1], line);
                        int value = registers.get(operands[0], line);
                        memory.write(address, value, line);
                        printTrace(line, instruction, "Memory[" + address + "] = " + value);
                    }
                    break;
                case "JMP":
                    requireOperands(opcode, operands, 1, line);
                    {
                        Integer target = resolveLabel(operands[0]);
                        if (target == null) {
                            stopWithError("Unknown label '" + operands[0] + "'", line);
                        }
                        pc = target;
                        printTrace(line, instruction, "Jump taken -> Line " + getLineNumber(target));
                        continue;
                    }
                case "JZ":
                    requireOperands(opcode, operands, 1, line);
                    if (alu.isZeroFlag()) {
                        Integer target = resolveLabel(operands[0]);
                        if (target == null) {
                            stopWithError("Unknown label '" + operands[0] + "'", line);
                        }
                        pc = target;
                        printTrace(line, instruction, "Jump taken -> Line " + getLineNumber(target));
                        continue;
                    }
                    printTrace(line, instruction, "Jump not taken");
                    break;
                case "JN":
                    requireOperands(opcode, operands, 1, line);
                    if (alu.isNegativeFlag()) {
                        Integer target = resolveLabel(operands[0]);
                        if (target == null) {
                            stopWithError("Unknown label '" + operands[0] + "'", line);
                        }
                        pc = target;
                        printTrace(line, instruction, "Jump taken -> Line " + getLineNumber(target));
                        continue;
                    }
                    printTrace(line, instruction, "Jump not taken");
                    break;
                case "JNZ":
                    requireOperands(opcode, operands, 1, line);
                    if (!alu.isZeroFlag()) {
                        Integer target = resolveLabel(operands[0]);
                        if (target == null) {
                            stopWithError("Unknown label '" + operands[0] + "'", line);
                        }
                        pc = target;
                        printTrace(line, instruction, "Jump taken -> Line " + getLineNumber(target));
                        continue;
                    }
                    printTrace(line, instruction, "Jump not taken");
                    break;
                case "PRINT":
                    requireOperands(opcode, operands, 1, line);
                    {
                        int value = registers.get(operands[0], line);
                        System.out.println(value);
                        printTrace(line, instruction, operands[0].toUpperCase() + " = " + value);
                    }
                    break;
                case "HALT":
                    requireOperands(opcode, operands, 0, line);
                    printTrace(line, instruction);
                    return;
                default:
                    stopWithError("Unknown opcode '" + instruction[0] + "'", line);
                    return;
            }

            pc = nextPc;
        }

        System.out.println("Warning: Execution reached the end of the program without HALT.");
    }

    private boolean isAluOpcode(String opcode) {
        for (String supported : ALU_OPCODES) {
            if (supported.equals(opcode)) {
                return true;
            }
        }
        return false;
    }

    private Integer resolveLabel(String label) {
        if (labels == null) {
            return null;
        }
        Integer target = labels.get(label);
        if (target != null) {
            return target;
        }
        String normalized = label.trim().toUpperCase();
        for (Map.Entry<String, Integer> entry : labels.entrySet()) {
            if (entry.getKey().trim().toUpperCase().equals(normalized)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private int getLineNumber(int instructionIndex) {
        if (lineNumbers != null && instructionIndex >= 0
                && instructionIndex < lineNumbers.length) {
            return lineNumbers[instructionIndex];
        }
        return instructionIndex + 1;
    }

    private void requireOperands(String opcode, String[] operands, int expected, int line) {
        if (operands.length != expected) {
            stopWithError("Invalid operand count for " + opcode + ": expected "
                + expected + ", got " + operands.length, line);
        }
    }

    private void stopWithError(String message, int line) {
        System.out.println("Error [Line " + line + "]: " + message + ".");
        System.exit(1);
    }

    private void printTrace(int line, String[] instruction) {
        printTrace(line, instruction, null);
    }

    private void printTrace(int line, String[] instruction, String detail) {
        StringBuilder text = new StringBuilder();
        text.append("[Line ").append(line).append("] ");
        for (int i = 0; i < instruction.length; i++) {
            if (i > 0) {
                text.append(" ");
            }
            text.append(instruction[i]);
        }
        if (detail != null) {
            text.append(" | ").append(detail);
        }
        if (isAluOpcode(instruction[0].trim().toUpperCase())) {
            text.append(" | ").append(alu.flagsToString());
        }
        System.out.println(text);
    }

    public int getPC() {
        return pc;
    }
}