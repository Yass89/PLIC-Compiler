package plic.repint;

public class Ecrire extends Instruction{

    Expression e;

    public Ecrire(Expression e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "Ecrire{" +
                "e=" + e +
                '}';
    }

    @Override
    public void verifier() {

    }
}
