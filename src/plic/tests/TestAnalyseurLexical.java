package plic.tests;

import plic.analyse.AnalyseurLexical;
import plic.exception.SyntaxException;

import java.io.File;
import java.io.FileNotFoundException;

public class TestAnalyseurLexical {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage : TestAnalyserLexical <nom du fichier>");
        } else {
            try {
                AnalyseurLexical sc = new AnalyseurLexical(new File("src/plic/sources/source0.plic"));
                String s;
                while (!(s = sc.next()).equals("EOF$")) {
                    System.out.println(s);
                }
            } catch (FileNotFoundException fnfe) {
                System.out.println("Fichier non trouvé");
            } catch (SyntaxException e) {
                e.printStackTrace();
            }
        }

    }
}
