//the memory structure is just array of 256 integer and this class make sure that the provided address is valid and then it will read or write to that address. It also includes error handling for invalid memory addresses.
public class Memory {
    private int[] ram = new int[256];

    // make sure address is between 0 and 255
    private void checkAddress(int address, int line) {
        if (address < 0 || address > 255) {
            System.out.println("Error [Line " + line + "]: Invalid memory address " + address + ".");
            System.exit(1);
        }
    }

    public int read(int address, int line) {
        checkAddress(address, line);
        return ram[address];
    }

    public void write(int address, int value, int line) {
        checkAddress(address, line);
        ram[address] = value;
    }
}