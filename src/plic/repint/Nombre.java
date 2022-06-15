package plic.repint;

import plic.exception.SemanticException;

import java.util.Objects;

public class Nombre extends Expression {

    private int val;

    public Nombre(int valeur) {
        val = valeur;
    }

    @Override
    public String toString() {
        return "Nombre{" +
                "val=" + val +
                '}';
    }

    @Override
    public void verifier() throws SemanticException {
        // ne fais rien
    }

    @Override
    public String getType() {
        return "Nombre";
    }

    @Override
    public String toMipsLHS() {
        return "\tli $a0, "+ val;
    }


    @Override
    public String toMipsRHS() {
        return "\tli $v0, "+ val;
    }

    @Override
    public String valeur() {
        return String.valueOf(val);
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nombre nombre = (Nombre) o;
        return val == nombre.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public String inverser() {
        if (!getNeg().equals(""))
            return "mul $v0, $v0, -1";
        else
            return "";
    }
}
