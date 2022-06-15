package plic.repint;

import plic.exception.SemanticException;

import java.util.Objects;

public class Ecrire extends Instruction {

    private Expression expression;

    public Ecrire(Expression e) {
        expression = e;
    }

    @Override
    public String toString() {
        return "Ecrire{" +
                "expression=" + expression.toString() +
                '}';
    }

    @Override
    public void verifier() throws SemanticException {
        expression.verifier();
    }

    @Override
    public String toMips() {
        String neg ="";
        if (!expression.getNeg().equals("")) {
            switch (expression.getType()) {
                case "Boolean" :
                    neg="non";
                    break;
                case "Entier" :
                    neg = "-";
                    break;
                default:
                    break;
            }
        }
        StringBuilder sb = new StringBuilder("# Ecrire "+neg +" "+ expression.valeur() + "\n" );
        sb.append(expression.toMipsRHS());
        sb.append("\n");
        sb.append(expression.inverser());
        sb.append("\n");
        sb.append("\n\t#ecriture\n\tmove $a0, $v0" +
                "\n\tli $v0 , 1" +
                "\n\tsyscall\n" +
                "\n#Retour à la ligne\n" +
                "\tli $v0, 4 \t# $v0 <- code du print\n" +
                "\tla $a0, str \t# $a0 <- adresse de la chaine a ecrire\n" +
                "\tsyscall \t# afficher");
        return sb.toString();
    }

    @Override
    public String getType() {
        return "ecrire";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ecrire ecrire = (Ecrire) o;
        return Objects.equals(expression, ecrire.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}
