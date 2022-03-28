package plic.repint;

import plic.exceptions.ErreurSemantique;

public class AccesTableau extends Acces {

    private Expression exp;

    public AccesTableau(Idf idf, Expression exp){
        super(idf);
        this.exp = exp;
    }


    @Override
    public void verifier() throws ErreurSemantique {
        exp.verifier();
    }


    @Override
    public Object getVal() {
        return null;
    }

    @Override
    public String toMIPS(){
        return "";
    }
}
