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

        // Met la valeur de l'expression dans $v0
        mips.append("li $v0, 1\n");
        if(e instanceof Nombre) {
            mips.append("li $a0, " + e.toMips() + "\n");
        }
        if(e instanceof Acces) {
            mips.append(e.toMips());
            mips.append("lw $a0, ($a0)\n");
        }
        if(e instanceof Idf) {
            mips.append("lw $a0, " + e.toMips() + "\n");
        }
        mips.append("syscall\n");
        return mips.toString();
    }
}
