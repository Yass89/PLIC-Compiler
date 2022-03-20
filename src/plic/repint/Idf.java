package plic.repint;
import plic.exceptions.ErreurSemantique;
import java.util.Map;

public class Idf extends Expression {
    private String nom;

    public Idf(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Idf{" +
                "nom='" + nom + '\'' +
                '}';
    }

    @Override
    public void verifier() throws ErreurSemantique {
        Map<Entree, Symbole> tableSymbole = TDS.getInstance().getTableSymboles();
        if (!tableSymbole.containsKey(new Entree(this.nom))) throw new ErreurSemantique("Idf non declare");
    }

    @Override
    public String toMips() {
        return null;
    }
}
