package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.exception.SemanticException;
import plic.exception.SyntaxException;
import plic.repint.Bloc;

import java.io.*;

public class Plic {

    public Plic(String nomFichier) throws FileNotFoundException {
        if (nomFichier.endsWith(".plic")) {
            AnalyseurSyntaxique analSyn = new AnalyseurSyntaxique(new File(nomFichier));
            try {
                Bloc bloc = analSyn.analyse();
                bloc.verifier();
                System.out.print(bloc.toMips());
            } catch (SyntaxException | SemanticException s) {
                System.out.println("ERREUR: À la ligne "+analSyn.getToken().getNumLigne()+" : " +s.getMessage());
            }
        } else {
            System.out.println("ERREUR: Suffixe incorrect");
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("ERREUR: Usage : Plic <nom du fichier>");
        } else {
            try {
                new Plic(args[0]);
            } catch (FileNotFoundException f) {
                System.out.println("ERREUR: "+f.getMessage());
            }
        }
    }
}
