package plic.repint;

public class InferieurOuEgal extends Boolean {
    @Override
    public String toMipsLHS() {
        return toMipsRHS();
    }

    @Override
    public String toMipsRHS() {
        return "\t#InferieurOuEgal\n"+super.toMipsRHS()+"\tsle $v0, $v1,$v0\n";
    }

    @Override
    public String valeur() {
        return e1.valeur()+" <= "+e2.valeur();
    }
}
