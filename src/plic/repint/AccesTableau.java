package plic.repint;

public class AccesTableau extends Acces {

    private Expression expr;
    public AccesTableau(Idf idf, Expression expr) {
        super(idf);
        this.expr = expr;
    }
}
