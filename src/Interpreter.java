public class Interpreter {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Please provide an input file path.");
            return;
        }

        Parser parser = new Parser();
        parser.parseFile(args[0]);

        Memory memory = new Memory();
        Registers registers = new Registers();
        ALU alu = new ALU(registers);

        CPU cpu = new CPU(
            registers,
            memory,
            alu,
            parser.getInstructions(),
            parser.getLabels(),
            parser.getLineNumbers()
        );
        cpu.run();
    }
}