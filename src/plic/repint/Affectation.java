package plic.repint;

import plic.exception.SemanticException;

import java.util.Objects;

public class Affectation extends Instruction {

    private Expression expression;
    private Acces acces;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Acces getIdentificateur() {
        return acces;
    }

    public void setIdentificateur(Acces acces) {
        this.acces = acces;
    }

    public Affectation(Expression e, Acces acces) {
        expression = e;
        this.acces=acces;
    }

    @Override
    public String toString() {
        return "Affectation{" +
                "expression=" + expression +
                ", identificateur=" + acces +
                '}';
    }

    @Override
    public void verifier() throws SemanticException {
        acces.verifier();
        if (expression.getType().equals("Boolean"))
            throw new SemanticException("Impossible d'affecter un boolean à un "+acces.getType().toLowerCase());
        expression.verifier();
    }

    @Override
    public String toMips() {
        String sb = "# "+acces.valeur()+" := "+expression.valeur()+"\n";
        sb+="\t#Acces\n";
        sb += acces.toMipsLHS();
        sb+="\n\n\t#Expression";
        sb+= "\n" +expression.toMipsRHS();
        sb += "\n\t#rangement dans v0\n\tsw $v0, ($a0)";
        return sb;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Affectation that = (Affectation) o;
        return Objects.equals(expression, that.expression) &&
                Objects.equals(acces, that.acces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, acces);
    }

    @Override
    public String getType() {
        return "affectation";
    }
}
