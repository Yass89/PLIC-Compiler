package plic.repint;

import plic.exceptions.ErreurSemantique;

import java.util.Map;

public class Nombre extends Expression{
    private int val;

    public Nombre(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "Nombre{" +
                "val=" + val +
                '}';
    }

    @Override
    public void verifier() throws ErreurSemantique {
        Map<Entree, Symbole> tableSymbole = TDS.getInstance().getTableSymboles();
        if (!tableSymbole.containsValue(new Symbole("entier"))) throw new ErreurSemantique("Nombre invalide");
    }

    @Override
    public Object getVal() {
        return val;
    }

}
