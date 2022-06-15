package plic.repint;

import plic.exception.SemanticException;

import java.util.ArrayList;

public class Bloc {

    private ArrayList<Instruction> instructions;
    private String nomProgramme;
    private static int labels;

    public Bloc(String nom) {
        instructions = new ArrayList<>();
        nomProgramme = nom;
        labels = 0 ;
    }

    public void ajouter(Instruction i) {
        instructions.add(i);
    }

    public int getSizeInstructions() {
        return instructions.size();
    }

    public boolean getInstruction(Instruction i) {
        return instructions.contains(i);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (Instruction i:
             instructions) {
            sb.append(i.toString()).append("\n");
        }
        String instruc = sb.toString();
        return "Bloc{" +
                instruc +
                '}';
    }

    public void verifier() throws SemanticException {
        if (instructions.size()==0)
            throw new SemanticException("au moins une instruction attendue");
        for (Instruction i:
             instructions) {
            i.verifier();
        }
    }

    public String toMips() {
        StringBuilder sb = new StringBuilder();
        if (nomProgramme!=null) {
            int nbVariables = TDS.getInstance().hashMapSize();
            sb.append("# programme ").append(nomProgramme);
            sb.append("\n");
            sb.append("# differentes String a afficher" +
                    "\n.data\n" +
                    "arrayIndexOutOfBoundsMessage: .asciiz \"ERREUR: Array index out of bounds\"" +
                    "\nstackOverflowMessage: .asciiz \"ERREUR: Stack overflow error\"" +
                    "\nstr: \t.asciiz \"\\n\"\n" +
                    "\n.text\n" +
                    "main:   \n" +
                    "\n# initialiser $s7 avec $sp\n" +
                    "\tmove $s7, $sp\n" +
                    "\n" +
                    "# reserver la place pour " + nbVariables + " variables\n" +
                    "\tadd $sp, $sp, " + nbVariables * (-4) + "\n");
            for (Instruction i :
                    instructions) {
                sb.append("\n").append(i.toMips()).append("\n");
            }
            sb.append("\n# code d'erreur a 0 car aucune erreur\n\tli $a0, 0" +
                    "\n\n# fin du programme " + nomProgramme +
                    "\nend :\n" +
                    "\tli $v0, 17\n" +
                    "\tsyscall\n\n");
            sb.append(outOfBoundsException());
            sb.append(stackOverflowError());
            return sb.toString();
        }
        else {
            for (Instruction i :
                    instructions) {
                sb.append("\n").append(i.toMips()).append("\n");
            }
            return sb.toString();
        }
    }


    private String outOfBoundsException() {
        return "arrayIndexOutOfBoundsException:\n" +
                "    move $t1, $v0\n" +
                "    li $v0, 4\n" +
                "    la $a0, arrayIndexOutOfBoundsMessage\n" +
                "    syscall\n" +
                "    li $a0, 1\n" +
                "    j end\n";
    }

    private String stackOverflowError() {
        return "\nstackOverflowException:\n" +
                "    move $t3, $v0\n" +
                "    li $v0, 4\n" +
                "    la $a0, stackOverflowMessage\n" +
                "    syscall\n" +
                "    li $a0, 2\n" +
                "    j end";
    }

    public static int getLabels() {
        return Bloc.labels;
    }

    public static void setLabels(int labels) {
        Bloc.labels = labels;
    }
}
