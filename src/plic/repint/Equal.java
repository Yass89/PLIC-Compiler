package plic.repint;

public class Equal extends Boolean {
    @Override
    public String toMipsLHS() {
        return toMipsRHS();
    }

    @Override
    public String toMipsRHS() {
        return super.toMipsRHS()+"\tseq $v0, $v1,$v0\n";
    }

    @Override
    public String valeur() {
        return e1.valeur()+" = "+e2.valeur();
    }
}
