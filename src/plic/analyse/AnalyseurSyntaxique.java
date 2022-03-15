package plic.analyse;
import plic.Consts;
import plic.exceptions.DoubleDeclaration;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.Entree;
import plic.repint.Symbole;
import plic.repint.TDS;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author unshade
 */
public class AnalyseurSyntaxique {

    /**
     * Analyseur lexical
     */
    private AnalyseurLexical analyseurLexical;

    /**
     * Unite lexicale courrante
     */
    private String uniteCourante;

    /**
     * Constructeur d'analyseur Syntaxique, utilise un analyseur lexical sur un fichier
     * @param file Fichier .plic a analyser
     * @throws FileNotFoundException Erreur si le fichier n'est pas trouve
     */
    public AnalyseurSyntaxique(File file) throws FileNotFoundException {
        analyseurLexical = new AnalyseurLexical(file);
    }

    /**
     * Analyse syntaxique globale d'un fichier .plic
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    public void analyse() throws ErreurSyntaxique, DoubleDeclaration {

        // L'untite lexicale courrante est initialisee
        this.uniteCourante = this.analyseurLexical.next();

        // Analyser lexicalement le programme
        this.analyseProg();

        // Verifier que la fin du .plic est bien EOF
        if (!this.uniteCourante.equals(Consts.EOF)) throw new ErreurSyntaxique("EOF Attendu");
    }

    /**
     * Analyse lexicale du programme plic
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private void analyseProg() throws ErreurSyntaxique, DoubleDeclaration {
        // Regarder si le premier mot est bien "programme"
        if (!this.uniteCourante.equals(Consts.PROG_START)) throw new ErreurSyntaxique("Programme attendu");
        // Passer au mot suivant (idf en temps normal)
        this.uniteCourante = this.analyseurLexical.next();
        // Verifier que ce mot est bien un idf
        if(!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        // Passage dans le bloc principal ( "{" )
        this.uniteCourante = this.analyseurLexical.next();
        this.analyseBloc();
    }

    /**
     * Permet de verifier que l'unite courrante correspond a un mot clef precis
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
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private void analyseBloc() throws ErreurSyntaxique, DoubleDeclaration {
        this.analyseTerminale(Consts.BLOC_OPEN);
        // Iterer sur analyseDeclaration et analyseInstruction tant qu'on est dans un bloc
        while (!this.uniteCourante.equals(Consts.BLOC_CLOSE)) {

            // Verifier si il s'agit d'une declaration ou d'une instruction
            if (uniteCourante.equals("entier")) {
                analyseDeclaration();
            } else analyseInstruction();
        }

        // Verifier qu'une fois en dehors de la boucle, il s'agit de la fin du bloc
        this.analyseTerminale(Consts.BLOC_CLOSE);
    }

    /**
     * Analyser si l'unite lexicale courrante est une instruction
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private void analyseInstruction() throws ErreurSyntaxique {

        // Verifier que l'unite lexicale est valide et qu'il ne s'agit pas du mot clef ecrire
        if (estIdf() && !this.uniteCourante.equals(Consts.PRINT)) {
            analyseAffectation();
        } else {
            analyseEcrire();
        }
    }

    /**
     * Analyser si le l'unite lexicale courrante est une instruction ecrire
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private void analyseEcrire() throws ErreurSyntaxique {

        // Regarder si l'UL est bien un "ecrire"
        analyseTerminale(Consts.PRINT);

        // Regarder le cas où l'UL n'est pas possible a ecrire
        if(!this.estConstanteEntiere()) {
            if (!estIdf()) {
                throw new ErreurSyntaxique("Ce n'est pas une constante entiere ou un Idf");
            }
        }
        this.uniteCourante = this.analyseurLexical.next();

        // Verifier que l'UL est bien un ;
        analyseTerminale(Consts.SEPARATEUR);
    }

    /**
     * Analyser les declarations
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private void analyseDeclaration() throws ErreurSyntaxique, DoubleDeclaration {

        Symbole symbole = new Symbole(this.uniteCourante);
        // Regarder que le type de la declaration est valide
        analyseTerminale(Consts.TYPE);

        // Verifier qu'il s'agit d'un IDF
        if(!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        Entree entry = new Entree(this.uniteCourante);
        this.uniteCourante = this.analyseurLexical.next();

        // Verifier que la fin de la declaration est bien un ;
        analyseTerminale(Consts.SEPARATEUR);

        TDS.getInstance().ajouter(entry, symbole);
    }

    /**
     * Analyser les affectations
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private void analyseAffectation() throws ErreurSyntaxique {

        // Analyse qu'il s'agit d'un acces
        analyseAcces();

        // Regarder que l'UL est un :=
        analyseTerminale(Consts.AFFECTATION);

        // Verifier qu'il s'agit d'une contante int ou d'un idf
        if(!this.estIdf()) {
            if(!this.estConstanteEntiere()) {
                throw new ErreurSyntaxique("Idf attendu");
            }
        }
        this.uniteCourante = this.analyseurLexical.next();

        // Verifier que l'UL est une ;
        analyseTerminale(Consts.SEPARATEUR);
    }

    /**
     * Analyser les acces
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private void analyseAcces() throws ErreurSyntaxique {
        // Verifier qu'il s'agit d'un Idf
        if(!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        this.uniteCourante = this.analyseurLexical.next();
    }

    /**
     * Analyser une expression
     * @throws ErreurSyntaxique Erreur Syntaxique dans le programme
     */
    private void analyseExpression() throws ErreurSyntaxique {

        // Verifier que l'UL est un Idf
        if(!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        this.uniteCourante = this.analyseurLexical.next();

        // Verifier que l'UL est un :=
        analyseTerminale(Consts.AFFECTATION);

        // Verifier que l'UL est une constante entiere
        if(!this.estConstanteEntiere()) {
            throw new ErreurSyntaxique("Ce n'est pas une constante entiere");
        }
        this.uniteCourante = this.analyseurLexical.next();
    }

    /**
     * Verifier qu'un mot est une constante entiere (Int)
     * @return l'unite lexicale courrante est un entier
     */
    private boolean estConstanteEntiere() {
        try {

            // Essayer de parse en Int l'unite courrante
            Integer.parseInt(this.uniteCourante);
        } catch(NumberFormatException | NullPointerException e) {

            // l'UL n'est pas un entier
            return false;
        }
        return true;
    }

    /**
     * Verifier qu'un Idf est valide
     * @return l'unite lexicale courrante est un Idf
     */
    private boolean estIdf() {
        // Vrai si le nom du programme est uniquement des lettres
        return ((!this.uniteCourante.equals("")) && (this.uniteCourante.matches(Consts.REGEX_IDF)));
    }


}
