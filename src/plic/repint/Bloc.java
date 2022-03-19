package plic.repint;

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

    public void verifier() {
        for (Instruction instruction : instructions) {
            if (instruction instanceof Affectation) {
                instruction.verifier();
            } else instruction.verifier();
        }
    }
}
