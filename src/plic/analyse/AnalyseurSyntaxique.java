package plic.analyse;

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
        if (!this.uniteCourante.equals("EOF")) throw new ErreurSyntaxique("EOF Attendu");
    }

    private void analyseProg() throws ErreurSyntaxique {
        // Regarder si le premier mot est bien "programme"
        if (!this.uniteCourante.equals("programme")) throw new ErreurSyntaxique("Programme attendu");
        // Passer au mot suivant (idf en temps normal)
        this.uniteCourante = this.analyseurLexical.next();
        // Verifier que ce mot est bien un idf
        if(!this.estIdf()) {
            throw new ErreurSyntaxique("idf attendu");
        }
        // Passage dans le bloc principal ( "{" )
        this.uniteCourante = this.analyseurLexical.next();
        this.analyseBloc();
    }

    private void analyseTerminale(String terminal) throws ErreurSyntaxique {
        if (!this.uniteCourante.equals(terminal)) throw new ErreurSyntaxique(terminal + "attendu");
        this.uniteCourante = this.analyseurLexical.next();
    }

    private void analyseBloc() throws ErreurSyntaxique {
        this.analyseTerminale("{");
        // Iterer sur analyseDeclaration tant qu'il y a des declarations
        this.analyseInstruction();
        // Iterer sur analyseInstruction tant qu'il y a des intructions
        this.analyseTerminale("}");
    }

    private void analyseInstruction() {
        
    }

    private boolean estIdf() {
        // Vrai si le nom du programme est uniquement des lettres
        return ((!this.uniteCourante.equals("")) && (this.uniteCourante.matches("^[a-zA-Z]*$")));
    }


}
