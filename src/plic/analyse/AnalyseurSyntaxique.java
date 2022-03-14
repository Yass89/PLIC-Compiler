package plic.analyse;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import plic.Consts;
import plic.exceptions.ErreurFile;
import plic.exceptions.ErreurSyntaxique;

import java.io.File;
import java.io.FileNotFoundException;

public class AnalyseurSyntaxique {

    private AnalyseurLexical analyseurLexical;
    private String uniteCourante;

    public AnalyseurSyntaxique(File file) throws FileNotFoundException {
        analyseurLexical = new AnalyseurLexical(file);
    }

    public void analyse() throws ErreurSyntaxique {
        this.uniteCourante = this.analyseurLexical.next();
        this.analyseProg();
        if (!this.uniteCourante.equals(Consts.EOF)) throw new ErreurSyntaxique("EOF Attendu");
    }

    private void analyseProg() throws ErreurSyntaxique {
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

    private void analyseTerminale(String terminal) throws ErreurSyntaxique {
        if (!this.uniteCourante.equals(terminal)) throw new ErreurSyntaxique(terminal + " attendu");
        this.uniteCourante = this.analyseurLexical.next();
    }

    private void analyseBloc() throws ErreurSyntaxique {
        this.analyseTerminale(Consts.BLOC_OPEN);
        // Iterer sur analyseDeclaration tant qu'il y a des declarations
        while (!this.uniteCourante.equals(Consts.BLOC_CLOSE)) {
            if (uniteCourante.equals("entier")) {
                analyseDeclaration();
            } else analyseInstruction();
        }
        // Iterer sur analyseInstruction tant qu'il y a des intructions
        this.analyseTerminale(Consts.BLOC_CLOSE);
    }

    private void analyseInstruction() throws ErreurSyntaxique {
        if (estIdf() && !this.uniteCourante.equals("ecrire")) {
            analyseAffectation();
        } else {
            analyseEcrire();
        }
    }

    private void analyseEcrire() throws ErreurSyntaxique {
        analyseTerminale(Consts.PRINT);
        if(!this.estConstanteEntiere()) {
            if (!estIdf()) {
                throw new ErreurSyntaxique("Ce n'est pas une constante entiere ou un Idf");
            }
        }
        this.uniteCourante = this.analyseurLexical.next();
        analyseTerminale(Consts.SEPARATEUR);
    }

    private void analyseDeclaration() throws ErreurSyntaxique {
        analyseTerminale(Consts.TYPE);
        if(!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        this.uniteCourante = this.analyseurLexical.next();
        analyseTerminale(Consts.SEPARATEUR);
    }

    private void analyseAffectation() throws ErreurSyntaxique {
        analyseAcces();
        analyseTerminale(Consts.AFFECTATION);
        if(!this.estIdf()) {
            if(!this.estConstanteEntiere()) {
                throw new ErreurSyntaxique("Idf attendu");
            }
        }
        this.uniteCourante = this.analyseurLexical.next();
        analyseTerminale(Consts.SEPARATEUR);
    }

    private void analyseAcces() throws ErreurSyntaxique {
        if(!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        this.uniteCourante = this.analyseurLexical.next();
    }

    private void analyseExpression() throws ErreurSyntaxique {
        if(!this.estIdf()) {
            throw new ErreurSyntaxique("Idf attendu");
        }
        this.uniteCourante = this.analyseurLexical.next();
        analyseTerminale(Consts.AFFECTATION);
        if(!this.estConstanteEntiere()) {
            throw new ErreurSyntaxique("Ce n'est pas une constante entiere");
        }
        this.uniteCourante = this.analyseurLexical.next();
        if (!estConstanteEntiere()) throw new ErreurSyntaxique("L'affectation ne se fait pas sur un entier");
    }

    private boolean estConstanteEntiere() {
        try {
            Integer.parseInt(this.uniteCourante);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private boolean estIdf() {
        // Vrai si le nom du programme est uniquement des lettres
        return ((!this.uniteCourante.equals("")) && (this.uniteCourante.matches(Consts.REGEX_IDF)));
    }


}
