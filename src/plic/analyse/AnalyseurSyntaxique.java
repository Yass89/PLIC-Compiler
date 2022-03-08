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
        if (!this.uniteCourante.equals("programme")) throw new ErreurSyntaxique("Programme attendu");
        this.uniteCourante = this.analyseurLexical.next();
        if(!this.estIdf()) {
            throw new ErreurSyntaxique("idf attendu");
        }
        this.uniteCourante = this.analyseurLexical.next();
    }

    private boolean estIdf() {
        return ((!this.uniteCourante.equals("")) && (this.uniteCourante.matches("^[a-zA-Z]*$")));
    }


}
