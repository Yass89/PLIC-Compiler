package plic.repint;

public class Multiplication extends Decimal {

    @Override
    public String toMipsRHS() {
        return "\t#Multiplication\n"+super.toMipsRHS()+"\tmul $v0, $v1, $v0\n"+stackOverflow();
    }

    @Override
    public String valeur() {
        return e1.valeur()+" * "+e2.valeur();
    }
}
