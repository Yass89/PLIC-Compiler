package plic.repint;

import plic.exceptions.ErreurSemantique;

/**
 * @author unshade
 */
public class Affectation extends Instruction {

    /**
     * Expression
     */
    Expression e;

    /**
     * Accès
     */
    Acces acces;

    /**
     * Constructeur d'affectation
     *
     * @param e   expression
     * @param acces acces
     */
    public Affectation(Expression e, Acces acces) {
        this.e = e;
        this.acces = acces;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "e=" + e +
                ", acces=" + acces +
                '}';
    }

    /**
     * Verifier la semantique de l'affectation
     *
     * @throws ErreurSemantique erreur semantique dans l'affectation'
     */
    @Override
    public void verifier() throws ErreurSemantique {
        acces.verifier();
        e.verifier();
    }

    /**
     * Convertir une affectation plic en mips
     *
     * @return le code mips
     */
    @Override
    public String toMips() {

        // Recuperer l'entree et le symbole
        Entree entree = new Entree(acces.getNom());
        Symbole symbole = TDS.getInstance().identifier(entree);
        StringBuilder mips = new StringBuilder();
        if(e instanceof Nombre) {
            mips.append("li $v0, " + e.toMips() + "\n");
        }
        if(e instanceof Acces) {
            mips.append(e.toMips());
            mips.append("lw $v0, ($a0)" + "\n");
        }
        if(e instanceof Idf) {
            mips.append("lw $v0, " + e.toMips() + "\n");
        }
        mips.append(this.acces.toMips());
        mips.append("sw $v0, ($a0)" + "\n");
        return mips.toString();

    }
}
