//the overall schema of the registers is as follows:
// R0 - R7: 8 general-purpose registers, each can hold an integer value
//it provides methods to get and set register values, as well as to retrieve values from either registers or plain numbers. It also includes error handling for invalid register names and number formats.
//you should provide the line number when calling any method that requires it, so that error messages can be more informative.
public class Registers {
    private int[] regs = new int[8]; // R0 to R7

    // get array index 0-7 from register name and make sure it's valid name
    private int getIndex(String name, int line) {
        name = name.trim().toUpperCase();
        if (name.length() == 2 && name.charAt(0) == 'R') {
            int idx = name.charAt(1) - '0';
            if (idx >= 0 && idx <= 7) return idx;
        }
        System.out.println("Error [Line " + line + "]: Invalid register name '" + name + "'.");
        System.exit(1);
        return -1;
    }

    public int get(String regName, int line) {
        return regs[getIndex(regName, line)];
    }

    public void set(String regName, int value, int line) {
        regs[getIndex(regName, line)] = value;
    }
    //just provide the parameter and then it will check if register or number and return the value, if not valid it will halt with error message
    // check if input is a register or a plain number
    public int getValue(String token, int line) {
        token = token.trim().toUpperCase();
        if (token.startsWith("R")) {
            return get(token, line);
        }
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            System.out.println("Error [Line " + line + "]: Invalid number or register '" + token + "'.");
            System.exit(1);
            return 0;
        }
    }
}