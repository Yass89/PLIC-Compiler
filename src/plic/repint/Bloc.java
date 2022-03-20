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
        for (Instruction instruction : instructions) {
            res.append(instruction.toMips());
        }
        return res.toString();
    }
}
