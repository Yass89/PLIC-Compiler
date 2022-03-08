package plic;

import plic.analyse.AnalyseurLexical;
import plic.analyse.AnalyseurSyntaxique;
import plic.exceptions.ErreurSyntaxique;

import java.io.File;
import java.io.FileNotFoundException;

public class Plic {

    public static void main(String[] args) throws FileNotFoundException, ErreurSyntaxique {
        new Plic(args[0]);
    }

    public Plic(String filePath) throws FileNotFoundException, ErreurSyntaxique {
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(new File(filePath));
        analyseurSyntaxique.analyse();
    }
}
