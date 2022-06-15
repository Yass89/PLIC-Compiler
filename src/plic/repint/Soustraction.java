package plic.repint;

public class Soustraction extends Decimal
{


    @Override
    public String toMipsRHS() {
        return "\t#Soustraction\n"+super.toMipsRHS()+"\tsub $v0, $v1, $v0\n" +stackOverflow();

    }

    @Override
    public String valeur() {
        return e1.valeur()+" - "+e2.valeur();
    }
}
