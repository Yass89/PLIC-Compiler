package plic.repint;

public class Affectation extends Instruction {

    Expression e;
    Idf idf;

    public Affectation(Expression e, Idf idf) {
        this.e = e;
        this.idf = idf;
    }
}
