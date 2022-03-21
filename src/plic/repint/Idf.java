package plic.repint;
import plic.exceptions.ErreurSemantique;
import java.util.Map;
import java.util.Objects;

public class Idf extends Expression {
    private final String nom;

    public Idf(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Idf{" +
                "nom='" + nom + '\'' +
                '}';
    }

    public String getNom() {
        return nom;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        Map<Entree, Symbole> tableSymbole = TDS.getInstance().getTableSymboles();
        if (!tableSymbole.containsKey(new Entree(this.nom))) throw new ErreurSemantique("Idf non declare");
    }

    @Override
    public Object getVal() {
        return nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Idf idf = (Idf) o;
        return Objects.equals(nom, idf.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}

