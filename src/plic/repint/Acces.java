package plic.repint;

public abstract class Acces extends Expression{

    private Idf idf;
    private Expression expression;

    public Acces(Idf idf) {
        this.idf = idf;
        this.expression = null;
    }

    public Idf getIdf() {
        return idf;
    }

    public void setIdf(Idf idf) {
        this.idf = idf;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }


}
