package plic.repint;

import plic.exceptions.ErreurSemantique;

import java.util.ArrayList;
import java.util.List;

/**
 * @author unshade
 */
public class Bloc {

    /**
     * Instructions du bloc
     */
    private List<Instruction> instructions;

    /**
     * Constructeur de bloc
     */
    public Bloc() {
        this.instructions = new ArrayList<>();
    }

    /**
     * Ajouter une instruction au bloc
     *
     * @param i instruction a ajouter
     */
    public void ajouter(Instruction i) {
        instructions.add(i);
    }

    /**
     * Verifier que les elements du blocs sont semantiquement corrects
     *
     * @throws ErreurSemantique erreur semantique
     */
    public void verifier() throws ErreurSemantique {
        for (Instruction instruction : instructions) {
            instruction.verifier();
        }
    }

    /**
     * Permet de convertir le code en assembleur mips
     *
     * @return code mips
     */
    public String toMips() {
        StringBuilder res = new StringBuilder();
        res.append(
                ".data" + "\n" +
                        "newLine : .ascii \"\\n\"\n" +
                        ".text" + "\n" +
                        "main :" + "\n"
        );
        res.append("add $sp,$sp,").append(TDS.getInstance().getCptDepl()).append("\n");
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
