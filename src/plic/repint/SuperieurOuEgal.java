package plic.repint;

public class SuperieurOuEgal extends Boolean{

    @Override
    public String toMipsRHS() {
        return "\t#SuperieurOuEgal\n"+super.toMipsRHS()+"\tsge $v0, $v1,$v0\n";
    }

    @Override
    public String valeur() {
        return e1.valeur()+" >= "+e2.valeur();
    }
}
