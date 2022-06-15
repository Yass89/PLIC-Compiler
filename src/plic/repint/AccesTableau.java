package plic.repint;

import plic.exception.SemanticException;

public class AccesTableau extends Acces {

    private Idf idf;
    private Expression expression;

    public AccesTableau(Idf idf,Expression expr) {
        super();
        this.idf = idf;
        expression=expr;
    }

    @Override
    public String getAddress() {
        return TDS.getInstance().getValue(new Entree(idf.getNom())).getDeplacement() + "($s7)";
    }

    @Override
    public void verifier() throws SemanticException {
        if (!TDS.getInstance().cleExiste(new Entree(idf.getNom()))) {
            throw new SemanticException("Variable "+idf.getNom()+" non déclarée");
        }
        if (!TDS.getInstance().getValue(new Entree((idf.getNom()))).getType().equals("Tableau"))
            throw new SemanticException("Variable "+idf.getNom()+" n'est pas un tableau");
        if (expression.getType().equals("Boolean")) {
            throw new SemanticException("L'acces du tableau ne peut pas être un booleen");
        }
        expression.verifier();
    }

    @Override
    public String getType() {
        return "Tableau";
    }

    @Override
    public String toMipsLHS() {
        String res = "";
        switch (expression.getType()) {
            case "Idf" :
                String addIdf = expression.toMipsRHS();
                res+= addIdf+"\n";
                break;
            case "Nombre" :
                res+= "\tli $v0, "+expression.valeur()+"\n";
                break;
            default: //tableau
                res+= expression.toMipsLHS();
                break;
        }
        return res +"\tli $t0, " +TDS.getInstance().getValue(new Entree(idf.getNom())).getTaille()+
                "\n\tbge $v0, $t0, arrayIndexOutOfBoundsException\n" +
                "# si on a un index en dehors de la definition du tableau alors ArrayIndexOutOfBoundsException\n" +
                "\tli $t1, 0\n" +
                "\tblt $v0, $t1, arrayIndexOutOfBoundsException\n" +
                "# si on a index negatif alors ArrayIndexOutOfBoundsException"
                +"\n\tla $a0, " + getAddress()+"\n"+
                "\tmulu $v0, $v0, 4\n" +
                "\tsubu $a0, $a0, $v0\n";

    }

    @Override
    public String toMipsRHS() {
        String res = "";
        switch (expression.getType()) {
            case "Idf" :
                String addIdf = expression.toMipsRHS();
                res= res+addIdf+"\n";
                break;
            case "Nombre" :
                res+= "\tli $v0, "+expression.valeur()+"\n";
                break;
            default: //tableau
                res+= expression.toMipsRHS();
                break;
        }
        return res +"\tli $t0, " +TDS.getInstance().getValue(new Entree(idf.getNom())).getTaille()+
                "\n\tbge $v0, $t0, arrayIndexOutOfBoundsException\n" +
                "# si on a un index en dehors de la definition du tableau alors ArrayIndexOutOfBoundsException\n" +
                "\tli $t1, 0\n" +
                "\tblt $v0, $t1, arrayIndexOutOfBoundsException\n" +
                "# si on a index negatif alors ArrayIndexOutOfBoundsException"
        +"\n\tla $t0, " + getAddress()+"\n"+
                "\tmulu $v0, $v0, 4\n" +
                "\tsubu $t0, $t0, $v0\n" +
                "\tlw $v0, ($t0)\n";
    }


    @Override
    public String valeur() {
        return idf.valeur()+" [ "+expression.valeur()+" ]";
    }
}
