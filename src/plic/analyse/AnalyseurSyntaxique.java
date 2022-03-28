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
        while (!this.uniteCourante.equals(Consts.BLOC_CLOSE)) {
            // Verifier si il s'agit d'une declaration ou d'une instruction
            if (uniteCourante.equals("entier")) {
                analyseDeclaration();
            } else bloc.ajouter(analyseInstruction());
        }

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
        if (estIdf() && !this.uniteCourante.equals(Consts.PRINT)) {
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

        Symbole symbole;
        Entree entry;
        if (this.uniteCourante.matches(Consts.ENTIER)) {
            symbole = new Symbole(this.uniteCourante);
            analyseTerminale(Consts.ENTIER);
            // Verifier qu'il s'agit d'un IDF
            if (!this.estIdf()) {
                throw new ErreurSyntaxique("Idf attendu");
            }
            entry = new Entree(this.uniteCourante);
            this.uniteCourante = this.analyseurLexical.next();
        } else {
            symbole = new Symbole(this.uniteCourante);
            analyseTerminale(Consts.TABLEAU);
            analyseTerminale(Consts.OUVERTURE_TAB);
            if (!pasConstanteEntiere()) {
                entry = new Entree(this.uniteCourante);
                analyseTerminale(Consts.REGEX_IDF);
            } else throw new ErreurSyntaxique("Declarer le tableau avec un nombre");
            analyseTerminale(Consts.FERMETURE_TAB);
        }
        // Verifier que la fin de la declaration est bien un ;
        analyseTerminale(Consts.SEPARATEUR);

        TDS.getInstance().ajouter(entry, symbole);
    }

    /**
     * Analyser les affectations
     *
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private Affectation analyseAffectation() throws ErreurSyntaxique {

        Idf idf = new Idf(this.uniteCourante);
        // Analyse qu'il s'agit d'un acces
        Acces acces = analyseAcces();

        // Analyseer qu'il s'agit d'une expression
        Expression e = analyseExpression();

        Affectation affectation = new Affectation(e, acces);

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
        Idf idf = new Idf(this.uniteCourante);
        // Verifier qu'il s'agit d'un Idf
        if (!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        acces = new Acces(idf);
        this.uniteCourante = this.analyseurLexical.next();
        if (this.uniteCourante.equals(Consts.OUVERTURE_TAB)) {
            Expression expr = analyseExpression();
            acces = new AccesTableau(idf, expr);

            if (!this.uniteCourante.equals(Consts.FERMETURE_TAB)) {
                throw new ErreurSyntaxique("] attendu");
            }
            this.uniteCourante = this.analyseurLexical.next();
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

        // Verifier que l'UL est un := si c'est dans le cas non ecrire
        if (!this.uniteCourante.equals(Consts.PRINT)) {
            analyseTerminale(Consts.AFFECTATION);
        } else this.uniteCourante = this.analyseurLexical.next();

        if (pasConstanteEntiere()) {
            expression = new Idf(this.uniteCourante);

        } else {
            expression = new Nombre(Integer.parseInt(this.uniteCourante));
        }

        this.uniteCourante = this.analyseurLexical.next();

        return expression;
    }

    /**
     * Verifier qu'un mot est une constante entiere (Int)
     *
     * @return l'unite lexicale courrante est un entier
     */
    private boolean pasConstanteEntiere() {
        try {

            // Essayer de parse en Int l'unite courrante
            Integer.parseInt(this.uniteCourante);
        } catch (NumberFormatException | NullPointerException e) {

            // l'UL n'est pas un entier
            return true;
        }
        return false;
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
