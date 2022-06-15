package plic.repint;

import plic.exception.SemanticException;

import java.util.ArrayList;
import java.util.Arrays;

public class Pour extends Iteration {
    private Idf idf;
    private Expression e1;
    private Expression e2;
    private Bloc bloc;
    private ArrayList<String> autorise = new ArrayList<>(Arrays.asList("Nombre", "Tableau", "Idf", "Entier"));

    public Pour(Idf idf, Expression e1, Expression e2, Bloc bloc) {
        this.idf = idf;
        this.e1 = e1;
        this.e2 = e2;
        this.bloc = bloc;
    }

    @Override
    public void verifier() throws SemanticException {
        if (!idf.getType().equals("Idf"))
            throw new SemanticException("idf attendu");
        idf.verifier();
        if (!autorise.contains(e1.getType()))
            throw new SemanticException("La premiere expression doit etre entiere");
        if (!autorise.contains(e2.getType()))
            throw new SemanticException("La seconde expression doit etre entiere");
        bloc.verifier();
    }

    @Override
    public String toMips() {
        StringBuilder sb = new StringBuilder();
        sb.append("#test si la premiere borne est bien plus petite que la deuxième");
        sb.append("\n");
        sb.append(e1.toMipsRHS());
        sb.append("\n");
        sb.append("\tmove $t2, $v0");
        sb.append("\n");
        sb.append(e2.toMipsRHS());
        sb.append("\n");
        sb.append("\tmove $t3, $v0");
        sb.append("\n");
        sb.append("\tsw $t3, ($sp)\n" +
                "\tsub $sp, $sp, 4\n");
        sb.append("\n");
        int label = Bloc.getLabels();
        sb.append("\tblt $t3, $t2, FinPour").append(label);
        sb.append("\n");
        sb.append("\tsw ").append("$t2, ").append(idf.getAddress());
        sb.append("\n");
        sb.append("#pour n°").append(label);
        sb.append("\n");
        sb.append("Pour").append(label).append(" :");
        sb.append("\n");
        Bloc.setLabels(label+1);
        sb.append(bloc.toMips());
        sb.append("\n");
        sb.append("#recuperation des valeurs nécessaires pour le test de la condition borne inf > borne sup");
        sb.append("\n");
        sb.append("\tlw $t2, ").append(idf.getAddress()).append("\n\tadd $sp, $sp, 4\n").append("\tlw $t3, ($sp)");
        sb.append("\n");
        sb.append("\taddi $t2, $t2, 1");
        sb.append("\n");
        sb.append("\tbgt $t2, $t3, FinPour").append(label);
        sb.append("\n");
        sb.append("\tsw ").append("$t2, ").append(idf.getAddress());
        sb.append("\n");
        sb.append("\tsub $sp, $sp, 4");
        sb.append("\n");
        sb.append("#boucle du pour");
        sb.append("\n");
        sb.append("j Pour").append(label);
        sb.append("\n");
        sb.append("#fin du pour");
        sb.append("\n");
        sb.append("FinPour").append(label).append(" :");
        return sb.toString();
    }

}
