package plic.repint;

public class Inferieur extends Boolean {
    @Override
    public String toMipsLHS() {
        return toMipsRHS();
    }

    @Override
    public String toMipsRHS() {
        return "\t#Inferieur\n"+super.toMipsRHS()+"\tslt $v0, $v1,$v0\n";
    }

    @Override
    public String valeur() {
        return e1.valeur()+" < "+e2.valeur();
    }
}
