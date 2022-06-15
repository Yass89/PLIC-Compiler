package plic.repint;

public class Ou extends CompletelyBoolean {

    @Override
    public String toMipsRHS() {
        return "\t#Ou\n"+super.toMipsRHS()+"\tor $v0, $v1,$v0\n";
    }

    @Override
    public String valeur() {
        return e1.valeur()+" ou "+e2.valeur();
    }
}
