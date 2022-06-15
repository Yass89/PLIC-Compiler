package plic.repint;

public class Somme extends Decimal {

    @Override
    public String toMipsRHS() {
        return "\t#Somme\n"+super.toMipsRHS()+"\tadd $v0, $v1, $v0\n"+stackOverflow();
    }

    @Override
    public String valeur() {
        return e1.valeur()+" + "+e2.valeur();
    }
}
