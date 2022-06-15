package plic.repint;

import plic.exception.SemanticException;

public class Condition extends Instruction {

    private Bloc b1;
    private Expression e;
    private Bloc b2;

    public Condition(Bloc bloc1, Expression expression, Bloc bloc2) {
        b1=bloc1;
        e=expression;
        b2=bloc2;
    }

    @Override
    public void verifier() throws SemanticException {
        if (!e.getType().equals("Boolean"))
            throw new SemanticException("L'expression doit etre booleen");
        b1.verifier();
        e.verifier();
        if(b2!=null)
            b2.verifier();
    }

    @Override
    public String toMips() {
        int label = Bloc.getLabels();
        StringBuilder sb = new StringBuilder();
        sb.append("#si n°").append(label);
        sb.append("\n");
        sb.append("Si").append(label).append(" :");
        sb.append("\n");
        sb.append(e.toMipsRHS());
        sb.append("\n");
        sb.append("#condition de validation");
        sb.append("\n");
        if (b2!=null) {
            sb.append("beqz $v0, Sinon").append(label);
        } else {
            sb.append("beqz $v0, FinSi").append(label);
        }
        sb.append("\n");
        Bloc.setLabels(label+1);
        sb.append(b1.toMips());
        sb.append("\n");
        if (b2!=null) {
            sb.append("#fin de branche");
            sb.append("\n");
            sb.append("j FinSi").append(label);
            sb.append("\n");
            sb.append("#branche alternative");
            sb.append("\n");
            sb.append("Sinon").append(label).append(" :");
            sb.append("\n");
            sb.append(b2.toMips());
            sb.append("\n");
        }
        sb.append("FinSi").append(label).append(" :");
        return sb.toString();
    }

    @Override
    public String getType() {
        return "Si";
    }

}
