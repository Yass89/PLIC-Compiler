package plic;

import plic.analyse.AnalyseurLexical;
import plic.analyse.AnalyseurSyntaxique;
import plic.exceptions.DoubleDeclaration;
import plic.exceptions.ErreurFile;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.Bloc;

import java.io.File;
import java.io.FileNotFoundException;

public class Plic {

    public static void main(String[] args) {
        try {
            new Plic(args[0]);
        } catch (FileNotFoundException | ErreurFile | ErreurSyntaxique | DoubleDeclaration e) {
            System.out.println(e.getMessage());
        }
    }

    public Plic(String filePath) throws FileNotFoundException, ErreurSyntaxique, ErreurFile, DoubleDeclaration {
        if (!filePath.endsWith(".plic")) throw new ErreurFile("Suffixe incorrect");
        File file = new File(filePath);
        if (!file.exists()) throw new ErreurFile("Fichier source absent");
        if (file.isDirectory()) throw new ErreurFile("Repertoire renseigné");
        if (file.isHidden()) throw new ErreurFile("Le fichier est caché");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
        Bloc bloc = analyseurSyntaxique.analyse();
        System.out.println(bloc);
    }
}
