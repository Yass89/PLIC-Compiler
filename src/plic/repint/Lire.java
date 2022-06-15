package plic.repint;

import plic.exception.SemanticException;

public class Lire extends Instruction {

    public Idf idf;

    public Lire(Idf identificateur) {
        idf = identificateur;
    }
    @Override
    public void verifier() throws SemanticException {
        if (!idf.getType().equals("Idf")) {
            throw new SemanticException("Mauvais identificateur");
        }
        idf.verifier();
    }

    @Override
    public String toMips() {
        return "# Lire un entier\n" +
                "\tli $v0 , 5\n" +
                "\tsyscall"+"\n" +
                "\tsw $v0, "+idf.getAddress();
    }

    @Override
    public String getType() {
        return "lire";
    }
}
