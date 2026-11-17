public class ALU {
    private Registers registers;
    private boolean zeroFlag = false;
    private boolean negativeFlag = false;

    public ALU(Registers registers) {
        this.registers = registers;
    }

    public void execute(String opcode, String[] operands) {
        // TODO (Member 2):
        // - Clear zeroFlag and negativeFlag before arithmetic (ADD, SUB, MUL, DIV) and CMP operations.
        // - Implement ADD, SUB, MUL, DIV (halt with error on division by zero).
        // - Implement AND, OR, XOR, NOT, SHL, SHR.
        // - Implement CMP (set Z if equal, N if Rx < Ry).
    }

    public boolean isZero() {
        return zeroFlag;
    }

    public boolean isNegative() {
        return negativeFlag;
    }
}