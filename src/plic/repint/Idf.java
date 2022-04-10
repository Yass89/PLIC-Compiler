package plic.repint;

import plic.exceptions.ErreurSemantique;

import java.util.Map;
import java.util.Objects;

/**
 * @author unshade
 */
public class Idf extends Expression {

    /**
     * Nom de l'idf
     */
    private final String nom;

    /**
     * Constructeur d'idf
     *
     * @param nom
     */
    public Idf(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Idf{" +
                "nom='" + nom + '\'' +
                '}';
    }

    /**
     * getter nom
     *
     * @return nom idf
     */
    public String getNom() {
        return nom;
    }

    /**
     * Verifier que l'idf est semantiquement correct
     *
     * @throws ErreurSemantique err semantique
     */
    @Override
    public void verifier() throws ErreurSemantique {
        Map<Entree, Symbole> tableSymbole = TDS.getInstance().getTableSymboles();
        if (!tableSymbole.containsKey(new Entree(this.nom))) throw new ErreurSemantique("Idf non declare");
    }

    @Override
    public String toMips() {
        Entree e = new Entree(this.nom);
        Symbole s = TDS.getInstance().identifier(e);
        return s.getDeplacement()+"($" +
                "sp)";
    }

    @Override
    public String getType() {
        return null;
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

