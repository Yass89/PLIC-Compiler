package plic.repint;

import plic.exceptions.ErreurSemantique;

/**
 * @author unshade
 */
public class Ecrire extends Instruction {

    /**
     * Expression a ecrire
     */
    Expression e;

    /**
     * Constructeur
     *
     * @param e expression
     */
    public Ecrire(Expression e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "Ecrire{" +
                "e=" + e +
                '}';
    }

    /**
     * Verifier que l'expression a ecrire est semantiquement correcte
     *
     * @throws ErreurSemantique erreur semantique
     */
    @Override
    public void verifier() throws ErreurSemantique {
        e.verifier();
    }


    /**
     * Permet de convertir l'instruction d'ecriture en assembleur mips
     *
     * @return le code mips
     */
    @Override
    public String toMips() {
        StringBuilder mips = new StringBuilder();
        if (e instanceof Idf) {
            Entree entree = new Entree((String) e.getVal());
            Symbole symbole = TDS.getInstance().identifier(entree);
            mips.append("li $v0 , 1\n" + "lw $a0 ,").append(symbole.getDeplacement()).append("($sp)").append("\n").append("syscall \n");
        } else {
            mips.append("li $v0 , 1\n" + "li $a0 ,").append(e.getVal()).append("\n").append("syscall \n");
        }
        mips.append("la $a0,newLine\n" +
                "addi $v0,$0,4\n" +
                "syscall\n");
        return mips.toString();
    }
}
