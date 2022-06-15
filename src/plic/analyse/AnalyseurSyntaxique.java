package plic.analyse;

import plic.exception.AtLeastOneInstructionException;
import plic.exception.DeclarationException;
import plic.exception.DoubleDeclaration;
import plic.exception.SyntaxException;
import plic.repint.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AnalyseurSyntaxique {

    private AnalyseurLexical anal;
    private String uniteCourante;
    private static final String erreurIDF = "Identificateur valide attendu";
    private static final String erreurCSTEntiere = "Constante entiere valide attendue";
    private static final String erreurAffectation = "Symbole := attendu";
    private static final String erreurEOF = "EOF attendu";
    private static final String erreurProgramme = "Mot clé programme attendu";

    public AnalyseurSyntaxique(File file) throws FileNotFoundException {
        anal = new AnalyseurLexical(file);
        uniteCourante = "";
    }

    private void changerUnite() throws SyntaxException {
        uniteCourante=anal.next();
    }

    public Bloc analyse() throws SyntaxException {
        changerUnite();
        Bloc b = analyseProg();
        if (!this.uniteCourante.equals(AnalyseurLexical.EOF)) {
            throw new SyntaxException(erreurEOF);
        }
        return b;
    }



    private Bloc analyseProg() throws SyntaxException {
        if (!uniteCourante.equals("programme"))
            throw new SyntaxException(erreurProgramme);
        changerUnite();
        if (!estIdf())
            throw new SyntaxException(erreurIDF);
        String nomProg = uniteCourante;
        changerUnite();
        return analyseBloc(nomProg);
    }

    private Bloc analyseBloc(String nomProg) throws SyntaxException {
        analyseTerminal("{");
        try {
            while (true)
                analyseDeclaration();
        } catch (DeclarationException e) {
            throw e;
        } catch (SyntaxException s) {
            //Laisse passer volantairement le programme car il est possible que aucune déclaration n'est été faite
        }
        try {
            if (isEnd())
                throw new AtLeastOneInstructionException();
        } catch (AtLeastOneInstructionException e) {
            throw e;
        }
        Bloc b = new Bloc(nomProg);
        do {
            Instruction i = analyseInstruction();
            b.ajouter(i);
        } while (!isEnd());
        analyseTerminal("}");
        return b;
    }

    private boolean isEnd() {
        return uniteCourante.equals("}") || uniteCourante.equals(AnalyseurLexical.EOF);
    }

    private void analyseDeclaration() throws SyntaxException {
        ArrayList<String> type = analyseType();
        if (!estIdf()) {
            throw new DeclarationException(erreurIDF);
        }
        String idf = uniteCourante;
        changerUnite();
        try {
            analyseTerminal(";");
            TDS tds = TDS.getInstance();
            if (type.get(0).equals("entier"))
                tds.ajouter(new Entree(idf),new SymboleEntier(type.get(0)));
            else
                tds.ajouter(new Entree(idf),new SymobleTableau(type.get(0),Integer.parseInt(type.get(1))));
        } catch (SyntaxException | DoubleDeclaration s) {
            throw new DeclarationException(s.getMessage());
        }
    }

    private ArrayList<String> analyseType() throws SyntaxException {
        ArrayList<String> res = new ArrayList<>();
        switch (uniteCourante) {
            case "entier" :
                analyseTerminal("entier");
                res.add("entier");
                break;
            case "tableau" :
                analyseTerminal("tableau");
                analyseTerminal("[");
                int i;
                try {
                    i = Integer.parseInt(uniteCourante);
                } catch (NumberFormatException e) {
                    throw new DeclarationException("Le tableau devrait avoir une constante entiere");
                }
                if (!estCstEntiere() || i <=0) {
                    throw new DeclarationException(erreurCSTEntiere);
                }
                String chiffre = uniteCourante;
                changerUnite();
                analyseTerminal("]");
                res.add("tableau");
                res.add(chiffre);
                break;
            default:
                throw new SyntaxException("");
        }
        return res;
    }

    private Instruction analyseInstruction() throws SyntaxException {
        Instruction i;
        switch (uniteCourante) {
            case "ecrire" :
            case "lire" :
                i = analyseES();
                break;
            case "pour" :
            case "tantque" :
                i = analyseIteration();
                break;
            case "si":
                i = analyseCondition();
                break;
            default:
                i=analyseAffectation();
                break;
        }
        return i;
    }

    private Instruction analyseIteration() throws SyntaxException {
        Instruction res = null;
        switch (uniteCourante) {
            case "pour":
                changerUnite();
                if(!estIdf())
                    throw new SyntaxException(erreurIDF);
                Idf idf = new Idf(uniteCourante);
                changerUnite();
                analyseTerminal("dans");
                Expression e1 = analyseExpression();
                analyseTerminal("..");
                Expression e2 = analyseExpression();
                analyseTerminal("repeter");
                Bloc bloc = analyseBloc(null);
                res = new Pour(idf,e1,e2,bloc);
                break;
            case "tantque":
                changerUnite();
                analyseTerminal("(");
                Expression etantque = analyseExpression();
                analyseTerminal(")");
                analyseTerminal("repeter");
                Bloc bloc1 = analyseBloc(null);
                res = new Tantque(etantque,bloc1);
                break;
            default:
                throw new SyntaxException("Mot cle inconnu");
        }
        return res;
    }

    private Instruction analyseCondition() throws SyntaxException {
        changerUnite();
        analyseTerminal("(");
        Expression e = analyseExpression();
        analyseTerminal(")");
        analyseTerminal("alors");
        Bloc b1 = analyseBloc(null);
        Bloc b2 = null;
        try {
            analyseTerminal("sinon");
            b2=analyseBloc(null);
        }catch (SyntaxException excep) {
            //laisse passer
        }
        return new Condition(b1,e,b2);
    }

    private Affectation analyseAffectation() throws SyntaxException {
        Affectation a = new Affectation(null,null);
        a.setIdentificateur(analyseAcces());
        if (!uniteCourante.equals(":=")) {
            throw new SyntaxException(erreurAffectation);
        }
        uniteCourante=anal.next();
        a.setExpression(analyseExpression());
        analyseTerminal(";");
        return a;
    }

    private Acces analyseAcces() throws SyntaxException {
        if (!estIdf())
            throw new SyntaxException(erreurIDF);
        return estAcces();
    }

    private Instruction analyseES() throws SyntaxException {
        Instruction res ;
        switch (uniteCourante) {
            case "ecrire" :
                changerUnite();
                res = new Ecrire(analyseExpression());
                break;
            case "lire" :
                changerUnite();
                if (!estIdf())
                    throw new SyntaxException(erreurIDF);
                res = new Lire(new Idf(uniteCourante));
                changerUnite();
                break;
            default:
                throw new SyntaxException("Mot cle inconnu");
        }
        analyseTerminal(";");
        return res;
    }

    private Expression analyseExpression() throws SyntaxException {
        Expression operande1 = analyseOperande();
        Operateur operateur;
        try {
            operateur = analyseOperateur();
        } catch (SyntaxException se) {
            return operande1;
        }
        Expression operande2 = analyseOperande();
        operateur.setE1(operande1);
        operateur.setE2(operande2);
        return operateur;
    }

    private Operateur analyseOperateur() throws SyntaxException {
        Operateur res;
        switch (uniteCourante) {
            case "+":
                res = new Somme();
                break;
            case "-" :
                res =  new Soustraction();
                break;
            case "*" :
                res = new Multiplication();
                break;
            case ">" :
                res = new Superieur();
                break;
            case "<" :
                res = new Inferieur();
                break;
            case "#" :
                res = new Different();
                break;
            case "=" :
                res = new Equal();
                break;
            case "<=" :
                res = new InferieurOuEgal();
                break;
            case ">=" :
                res = new SuperieurOuEgal();
                break;
            case "et" :
                res = new Et();
                break;
            case "ou" :
                res = new Ou();
                break;
            default:
                throw new SyntaxException("L'operateur est inconnu");
        }
        changerUnite();
        return res;
    }

    private Expression analyseOperande() throws SyntaxException {
        Expression e ;
        if (!estCstEntiere()) {
            switch (uniteCourante) {
                case "non" :
                    changerUnite();
                    e = analyseExpression();
                    e.setNeg("Non");
                    break;
                case "-" :
                    changerUnite();
                    analyseTerminal("(");
                    e = analyseExpression();
                    e.setNeg("Moins");
                    analyseTerminal(")");
                    break;
                case "(" :
                    changerUnite();
                    e = analyseExpression();
                    analyseTerminal(")");
                    break;
                default:
                    e = estAcces();
                    break;
            }
        } else {
            try {
                e = new Nombre(Integer.parseInt(uniteCourante));
            } catch (NumberFormatException nfe) {
                throw new SyntaxException("Nombre trop grand pour un entier");
            }
            changerUnite();
        }
        return e;
    }

    private Acces estAcces() throws SyntaxException {
        String idf = uniteCourante;
        boolean resVar = estIdf();
        changerUnite();
        try {
            if (resVar) {
                analyseTerminal("[");
            }
        } catch (SyntaxException s) {
            return new Idf(idf);
        }
        Expression e = analyseExpression();
        analyseTerminal("]");
        return new AccesTableau(new Idf(idf),e);
    }

    private void analyseTerminal(String terminal) throws SyntaxException {
        if (!uniteCourante.equals(terminal))
            throw new SyntaxException(terminal+" attendu");
        changerUnite();
    }

    private boolean estIdf() {
        return uniteCourante.matches("[a-zA-Z]+");
    }

    private boolean estCstEntiere () {
        return uniteCourante.matches("[0-9]+");
    }

    public Token getToken() {
        return anal.getToken();
    }
}
