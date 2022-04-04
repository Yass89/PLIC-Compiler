package plic.repint;

import plic.exceptions.ErreurSemantique;

import java.util.Map;

/**
 * @author unshade
 */
public class Nombre extends Expression {

    /**
     * Valeur du nombre
     */
    private int val;

    /**
     * Constructeur de valeur
     *
     * @param val valeur
     */
    public Nombre(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Nombre{" +
                "val=" + val +
                '}';
    }

    /**
     * Verifier que le nombre est semantquement correct
     *
     * @throws ErreurSemantique err semantique
     */
    @Override
    public void verifier() throws ErreurSemantique {
        Map<Entree, Symbole> tableSymbole = TDS.getInstance().getTableSymboles();
        if (!tableSymbole.containsValue(new Symbole("entier"))) throw new ErreurSemantique("Nombre invalide");
    }

    @Override
    public String toMips() {
        return  val + "";
    }

    @Override
    public Object getVal() {
        return val;
    }

}
