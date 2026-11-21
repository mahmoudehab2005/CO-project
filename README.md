# COSP Assembly Code Interpreter

A Java-based virtual machine that reads a plain-text assembly program and simulates the fetch-decode-execute cycle.

## Project Architecture

1. **Parser & Loader (`Parser.java`)**: Reads the input `.txt` file, strips comments and whitespace, maps jump labels, tracks original line numbers, and builds the instruction array.
2. **Hardware & ALU (`Memory.java`, `Registers.java`, `ALU.java`)**: Models the 256-cell RAM, registers `R0`–`R7`, status flags (`Z`, `N`), bounds validation, and all arithmetic, bitwise, shift, and comparison operations.
3. **Control Unit & Execution (`CPU.java`, `Interpreter.java`)**: Manages the Program Counter (`PC`), runs the fetch-decode-execute loop, handles data movement and branching, and prints the execution trace.

## Team Division of Labor

* **Youssef — Parser & Loader (`Parser.java`)**
  * Handle file loading and missing-file error reporting.
  * Strip `;` comments and extra whitespace; enforce case-insensitive opcodes and comma-separated operands.
  * Map labels (`label:`) to their target instruction index and record original file line numbers.
* **Hossam — Hardware & ALU (`Memory.java`, `Registers.java`, `ALU.java`)**
  * Implement RAM (`int[256]`) and registers (`R0`–`R7`) with bounds and name validation.
  * Implement `ADD`, `SUB`, `MUL`, `DIV` (including division-by-zero checks), `AND`, `OR`, `XOR`, `NOT`, `SHL`, `SHR`, and `CMP`.
  * Manage resetting and updating the Zero (`Z`) and Negative (`N`) flags.
* **Mahmoud — Control Unit & Output (`CPU.java`, `Interpreter.java`)**
  * Run the fetch-decode-execute loop using `PC`.
  * Implement `MOV`, `LOAD`, `STORE`, `JMP`, `JZ`, `JN`, `JNZ`, `PRINT`, and `HALT`.
  * Handle unknown opcodes, missing-`HALT` warnings, and formatted step-by-step console trace output.