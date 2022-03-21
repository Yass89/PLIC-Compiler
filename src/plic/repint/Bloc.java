package plic.repint;

import plic.exceptions.ErreurSemantique;

import java.util.ArrayList;
import java.util.List;


public class Bloc {

    private List<Instruction> instructions;

    public Bloc() {
        this.instructions = new ArrayList<>();
    }

    public void ajouter(Instruction i) {
        instructions.add(i);
    }

    public void verifier() throws ErreurSemantique {
        for (Instruction instruction : instructions) {
            instruction.verifier();
        }
    }

    public String toMips() {
        StringBuilder res = new StringBuilder();
        res.append(
                ".data" + "\n" +
                "newLine : .ascii \"\\n\"\n" + "\n" +
                ".text" + "\n" +
                "main :" + "\n"
        );
        res.append("add $sp,$sp,").append(TDS.getInstance().getCptDepl());
        for (Instruction instruction : instructions) {
            res.append(instruction.toMips());
        }
        res.append(
                "end :" + "\n" +
                        "li $v0,10" + "\n" +
                        "syscall"
        );
        return res.toString();
    }
}
