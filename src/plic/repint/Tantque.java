package plic.repint;

import plic.exception.SemanticException;

public class Tantque extends Iteration {

    private Expression e;
    private Bloc bloc;

    public Tantque(Expression etantque, Bloc bloc1) {
        e=etantque;
        bloc=bloc1;
    }

    @Override
    public void verifier() throws SemanticException {
        if (!e.getType().equals("Boolean"))
            throw new SemanticException("L'expression doit etre booleen");
        e.verifier();
        bloc.verifier();
    }

    @Override
    public String toMips() {
        int label = Bloc.getLabels();
        StringBuilder sb = new StringBuilder();
        sb.append("#tantque n°").append(label);
        sb.append("\n");
        sb.append("Tantque").append(label).append(" :");
        sb.append("\n");
        sb.append(e.toMipsRHS());
        sb.append("\n");
        sb.append("#condition de validation");
        sb.append("\n");
        sb.append("beqz $v0, FinTantque").append(label);
        sb.append("\n");
        Bloc.setLabels(label+1);
        sb.append(bloc.toMips());
        sb.append("\n");
        sb.append("#fin de branche");
        sb.append("\n");
        sb.append("j Tantque").append(label);
        sb.append("\n");
        sb.append("FinTantque").append(label).append(" :");
        return sb.toString();
    }

}
