package plic.repint;

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
}
