package plic.repint;

public class Superieur extends Boolean {

    @Override
    public String toMipsRHS() {
        return "\t#Superieur\n"+super.toMipsRHS()+"\tsgt $v0, $v1,$v0\n";
    }

    @Override
    public String valeur() {
        return e1.valeur()+" > "+e2.valeur();
    }
}
