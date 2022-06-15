package plic.repint;

import java.util.Objects;

public class Symbole {

    protected String type;
    protected int deplacement;
    protected int taille;

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbole symbole = (Symbole) o;
        return deplacement == symbole.deplacement &&
                Objects.equals(type, symbole.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, deplacement);
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }


    public Symbole(String type) {
        this.type=type;
        this.deplacement=1;
        taille = 1 ;
    }

    public Symbole(String type, int taille) {
        this.type=type;
        this.deplacement=1;
        this.taille = taille;
    }

    public int getTaille() {
        return taille;
    }
}
