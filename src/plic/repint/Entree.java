package plic.repint;

import java.util.Objects;

/**
 * @author unshade
 */
public class Entree {

    /**
     * Idf de l'entree
     */
    private String idf;

    /**
     * Constructeur de l'entree
     *
     * @param idf idf
     */
    public Entree(String idf) {
        this.idf = idf;
    }

    /**
     * getter idf
     *
     * @return idf
     */
    public String getIdf() {
        return idf;
    }

    /**
     * setter idf
     *
     * @param idf new idf
     */
    public void setIdf(String idf) {
        this.idf = idf;
    }

    @Override
    public String toString() {
        return "Entree{" +
                "idf='" + idf + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entree entree = (Entree) o;
        return Objects.equals(idf, entree.idf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idf);
    }
}
