package plic.repint;

import plic.exception.SemanticException;

import java.util.Objects;

public class Idf extends Acces {

    private String nom;

    public Idf(String nom){
        this.nom=nom;
    }

    @Override
    public String toString() {
        return "Idf{" +
                "nom='" + nom + '\'' +
                '}';
    }

    @Override
    public void verifier() throws SemanticException {
        if (!TDS.getInstance().cleExiste(new Entree(nom)))
            throw new SemanticException("Variable "+nom+" non déclarée");
        if (!TDS.getInstance().getValue(new Entree(nom)).getType().equals("Entier"))
            throw new SemanticException("Variable "+nom+" n'est pas un entier");
    }

    @Override
    public String getType() {
        return "Idf";
    }

    @Override
    public String toMipsLHS() {
        String res = "";
        res+= "\tla $a0, " + getAddress();
        return res;
    }

    @Override
    public String toMipsRHS() {
        return "\tlw $v0, "+TDS.getInstance().identifier(new Entree(nom)).getDeplacement() +"($s7)";
    }

    @Override
    public String valeur() {
        return nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    @Override
    public String getAddress() {
        return TDS.getInstance().getValue(new Entree(nom)).getDeplacement() + "($s7)";
    }
}
