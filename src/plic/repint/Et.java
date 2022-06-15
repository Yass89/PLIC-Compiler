package plic.repint;

public class Et extends CompletelyBoolean {

    @Override
    public String toMipsRHS() {
        return "\t#Et\n"+super.toMipsRHS()+"\tand $v0, $v1,$v0\n";
    }

    @Override
    public String valeur() {
        return e1.valeur()+" et "+e2.valeur();
    }
}
