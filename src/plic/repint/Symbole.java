package plic.repint;

import java.util.Objects;

public class Symbole {

    private String type;
    private int deplacement;

    public Symbole(String type, int deplacement) {
        this.type = type;
        this.deplacement = deplacement;
    }

    public Symbole(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbole symbole = (Symbole) o;
        return deplacement == symbole.deplacement && Objects.equals(type, symbole.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, deplacement);
    }
}
