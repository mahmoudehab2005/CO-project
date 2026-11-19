//it contains the ALU class which is responsible for executing arithmetic and logical operations on the registers.
//  It supports various operations like addition, subtraction, multiplication, division, bitwise operations, and comparison. The ALU also maintains flags for zero and negative results, which can be used for conditional branching in the program.
public class ALU {
    private Registers regs;
    private boolean zeroFlag = false;
    private boolean negativeFlag = false;

    public ALU(Registers regs) {
        this.regs = regs;
    }

    public void execute(String opcode, String[] operands) {
        switch (opcode.toUpperCase()) {
            case "ADD":
                add(operands[0], operands[1], operands[2]);
                break;
            case "SUB":
                sub(operands[0], operands[1], operands[2]);
                break;
            case "MUL":
                mul(operands[0], operands[1], operands[2]);
                break;
            case "DIV":
                div(operands[0], operands[1], operands[2]);
                break;
            case "AND":
                and(operands[0], operands[1], operands[2]);
                break;
            case "OR":
                or(operands[0], operands[1], operands[2]);
                break;
            case "XOR":
                xor(operands[0], operands[1], operands[2]);
                break;
            case "NOT":
                not(operands[0], operands[1]);
                break;
            case "SHL":
                shl(operands[0], operands[1], operands[2]);
                break;
            case "SHR":
                shr(operands[0], operands[1], operands[2]);
                break;
            case "CMP":
                cmp(operands[0], operands[1]);
                break;
            default:
                System.out.println("Error: Unknown ALU opcode '" + opcode + "'.");
                System.exit(1);
        }
    }

    // clear and update flags after math ops
    private void updateFlags(int result) {
        zeroFlag = (result == 0);
        negativeFlag = (result < 0);
    }

    private void add(String rx, String ry, String rz) {
        int res = regs.get(ry) + regs.getValue(rz);
        regs.set(rx, res);
        updateFlags(res);
    }

    private void sub(String rx, String ry, String rz) {
        int res = regs.get(ry) - regs.getValue(rz);
        regs.set(rx, res);
        updateFlags(res);
    }

    private void mul(String rx, String ry, String rz) {
        int res = regs.get(ry) * regs.getValue(rz);
        regs.set(rx, res);
        updateFlags(res);
    }

    private void div(String rx, String ry, String rz) {
        int divisor = regs.getValue(rz);
        if (divisor == 0) {
            System.out.println("Error: Division by zero.");
            System.exit(1);
        }
        int res = regs.get(ry) / divisor;
        regs.set(rx, res);
        updateFlags(res);
    }

    // bitwise and shift ops don't touch flags
    private void and(String rx, String ry, String rz) {
        regs.set(rx, regs.get(ry) & regs.get(rz));
    }

    private void or(String rx, String ry, String rz) {
        regs.set(rx, regs.get(ry) | regs.get(rz));
    }

    private void xor(String rx, String ry, String rz) {
        regs.set(rx, regs.get(ry) ^ regs.get(rz));
    }

    private void not(String rx, String ry) {
        regs.set(rx, ~regs.get(ry));
    }

    private void shl(String rx, String ry, String n) {
        regs.set(rx, regs.get(ry) << regs.getValue(n));
    }

    private void shr(String rx, String ry, String n) {
        regs.set(rx, regs.get(ry) >> regs.getValue(n));
    }

    private void cmp(String rx, String ry) {
        int x = regs.get(rx);
        int y = regs.get(ry);
        zeroFlag = (x == y);
        negativeFlag = (x < y);
    }

    public boolean isZeroFlag() {
        return zeroFlag;
    }

    public boolean isNegativeFlag() {
        return negativeFlag;
    }

    // format flags for output trace
    public String flagsToString() {
        return "Z=" + (zeroFlag ? 1 : 0) + ", N=" + (negativeFlag ? 1 : 0);
    }
}