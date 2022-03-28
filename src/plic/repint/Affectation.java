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
     * Acces
     */
    Acces acces;

    /**
     * Constructeur d'affectation
     *
     * @param e   expression
     * @param acces son idf
     */
    public Affectation(Expression e, Acces acces) {
        this.e = e;
        this.acces = acces;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "e=" + e +
                ", idf=" + acces +
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
        Entree entree = new Entree(acces.getIdf().getNom());
        Symbole symbole = TDS.getInstance().identifier(entree);
        StringBuilder mips = new StringBuilder();
        if (e instanceof Idf) {
            Entree entree2 = new Entree((String) e.getVal());
            Symbole s2 = TDS.getInstance().identifier(entree2);
            mips.append("lw $v0,").append(s2.getDeplacement()).append("($sp)").append("\n").append("sw $v0,").append(symbole.getDeplacement()).append("($sp)").append("\n");
        } else
            mips.append("li $v0,").append(e.getVal()).append("\n").append("sw $v0,").append(symbole.getDeplacement()).append("($sp)\n");
        return mips.toString();
    }
}
