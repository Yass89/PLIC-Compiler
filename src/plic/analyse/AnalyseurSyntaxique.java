package plic.analyse;

import plic.Consts;
import plic.exceptions.DoubleDeclaration;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.*;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author unshade
 */
public class AnalyseurSyntaxique {

    /**
     * Analyseur lexical
     */
    private final AnalyseurLexical analyseurLexical;

    /**
     * Unite lexicale courrante
     */
    private String uniteCourante;

    /**
     * Constructeur d'analyseur Syntaxique, utilise un analyseur lexical sur un fichier
     *
     * @param file Fichier .plic a analyser
     * @throws FileNotFoundException Erreur si le fichier n'est pas trouve
     */
    public AnalyseurSyntaxique(File file) throws FileNotFoundException {
        analyseurLexical = new AnalyseurLexical(file);
    }

    /**
     * Analyse syntaxique globale d'un fichier .plic
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    public Bloc analyse() throws ErreurSyntaxique, DoubleDeclaration {

        // L'untite lexicale courrante est initialisee
        this.uniteCourante = this.analyseurLexical.next();

        // Analyser lexicalement le programme
        Bloc bloc = this.analyseProg();

        // Verifier que la fin du .plic est bien EOF
        if (!this.uniteCourante.equals(Consts.EOF)) throw new ErreurSyntaxique("EOF Attendu");

        return bloc;
    }

    /**
     * Analyse lexicale du programme plic
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private Bloc analyseProg() throws ErreurSyntaxique, DoubleDeclaration {
        // Regarder si le premier mot est bien "programme"
        if (!this.uniteCourante.equals(Consts.PROG_START)) throw new ErreurSyntaxique("Programme attendu");
        // Passer au mot suivant (idf en temps normal)
        this.uniteCourante = this.analyseurLexical.next();
        // Verifier que ce mot est bien un idf
        if (!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu : nom du programme");
        }
        // Passage dans le bloc principal ( "{" )
        this.uniteCourante = this.analyseurLexical.next();
        return this.analyseBloc();
    }

    /**
     * Permet de verifier que l'unite courrante correspond a un mot clef precis
     *
     * @param terminal mot clef
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme (terminal innatendu)
     */
    private void analyseTerminale(String terminal) throws ErreurSyntaxique {
        // Verifier que l'unite courrante est bien un mot clef attendu
        if (!this.uniteCourante.equals(terminal)) throw new ErreurSyntaxique(terminal + " attendu");
        // Passer a l'unite lexicale suivante
        this.uniteCourante = this.analyseurLexical.next();
    }

    /**
     * Analyser l'intérieur d'un bloc (moceau de texte entre { et })
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private Bloc analyseBloc() throws ErreurSyntaxique, DoubleDeclaration {

        Bloc bloc = new Bloc();

        // Regarder qu'on commence par {
        this.analyseTerminale(Consts.BLOC_OPEN);
        // Iterer sur analyseDeclaration et analyseInstruction tant qu'on est dans un bloc

        // Verifier si il s'agit d'une declaration ou d'une instruction
        while (uniteCourante.equals("entier") || uniteCourante.equals("tableau")) {
            analyseDeclaration();
        }
        bloc.ajouter(analyseInstruction());

        while (!this.uniteCourante.equals(Consts.BLOC_CLOSE))
            bloc.ajouter(analyseInstruction());


        // Verifier qu'une fois en dehors de la boucle, il s'agit de la fin du bloc
        this.analyseTerminale(Consts.BLOC_CLOSE);

        return bloc;
    }

    /**
     * Analyser si l'unite lexicale courrante est une instruction
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private Instruction analyseInstruction() throws ErreurSyntaxique {

        Instruction instruction;
        // Verifier que l'unite lexicale est valide et qu'il ne s'agit pas du mot clef ecrire
        if (!this.uniteCourante.equals(Consts.PRINT)) {
            instruction = analyseAffectation();

        } else {
            instruction = analyseEcrire();
        }
        return instruction;
    }

    /**
     * Analyser si le l'unite lexicale courrante est une instruction ecrire
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private Ecrire analyseEcrire() throws ErreurSyntaxique {


        analyseTerminale("ecrire");

        // Analyser l'expression
        Expression e = analyseExpression();

        Ecrire ecrire = new Ecrire(e);

        // Verifier que l'UL est bien un ;
        analyseTerminale(Consts.SEPARATEUR);

        return ecrire;
    }

    /**
     * Analyser les declarations
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private void analyseDeclaration() throws ErreurSyntaxique, DoubleDeclaration {
        Entree entree;
        Symbole symbole = new Symbole(this.uniteCourante);
        // Regarder que le type de la declaration est valide
        analyseType();

        // Verifier qu'il s'agit d'un IDF
        if (!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        entree = new Entree(this.uniteCourante);
        this.uniteCourante = this.analyseurLexical.next();

        // Verifier que la fin de la declaration est bien un ;
        analyseTerminale(Consts.SEPARATEUR);

        TDS.getInstance().ajouter(entree, symbole);
    }

    private void analyseType() throws ErreurSyntaxique {
        if (this.uniteCourante.equals("entier") || this.uniteCourante.equals("tableau")) {
            this.uniteCourante = this.analyseurLexical.next();
        } else {
            throw new ErreurSyntaxique("Type attendu");
        }
    }

    /**
     * Analyser les affectations
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private Affectation analyseAffectation() throws ErreurSyntaxique {

        // Analyse qu'il s'agit d'un acces
        analyseAcces();

        analyseTerminale(Consts.AFFECTATION);
        // Analyseer qu'il s'agit d'une expression
        Expression e = analyseExpression();
        Idf idf = new Idf(this.uniteCourante);

        Affectation affectation = new Affectation(e, idf);

        // Verifier que l'UL est une ;
        analyseTerminale(Consts.SEPARATEUR);

        return affectation;
    }

    /**
     * Analyser les acces
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private Acces analyseAcces() throws ErreurSyntaxique {
        Acces acces;
        // Verifier qu'il s'agit d'un Idf
        if (!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        Idf idf = new Idf(this.uniteCourante);
        acces = new Acces(idf);
        this.uniteCourante = this.analyseurLexical.next();

        if (this.uniteCourante.equals("[")) {
            this.uniteCourante = this.analyseurLexical.next();
            Expression e = analyseExpression();
            acces = new AccesTableau(idf, e);
            analyseTerminale("]");
        }

        return acces;
    }

    /**
     * Analyser une expression
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private Expression analyseExpression() throws ErreurSyntaxique {


        Expression expression;

        if (!estIdf() && pasConstanteEntiere()) {
            throw new ErreurSyntaxique("idf ou entier attendu");
        } else if (estIdf())
            expression = analyseAcces();
        else {
            expression = new Nombre(Integer.parseInt(uniteCourante));
            this.uniteCourante = this.analyseurLexical.next();
        }

        return expression;
    }

    /**
     * Verifier qu'un mot est une constante entiere (Int)
     *
     * @return l'unite lexicale courrante est un entier
     */
    private boolean pasConstanteEntiere() {
        return !this.uniteCourante.matches("[0-9]+");
    }

    /**
     * Verifier qu'un Idf est valide
     *
     * @return l'unite lexicale courrante est un Idf
     */
    private boolean estIdf() {
        // Vrai si le nom du programme est uniquement des lettres
        return ((!this.uniteCourante.equals("")) && (this.uniteCourante.matches(Consts.REGEX_IDF)));
    }


}
