package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.exceptions.DoubleDeclaration;
import plic.exceptions.ErreurFile;
import plic.exceptions.ErreurSemantique;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.Bloc;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author unshade
 */
public class Plic {

    /**
     * Methode principale
     *
     * @param args Un seul arg : args[0] = le path du fichier a compiler
     */
    public static void main(String[] args) {
        try {
            new Plic(args[0]);
        } catch (FileNotFoundException | ErreurFile | ErreurSyntaxique | DoubleDeclaration | ErreurSemantique e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
    }

    /**
     * Constructeur du comilateur
     *
     * @param filePath path du fichier a compiler
     * @throws FileNotFoundException fichier non prent
     * @throws ErreurSyntaxique      erreur syntaxique dans la source
     * @throws ErreurFile            erreur dans le fichier
     * @throws DoubleDeclaration     double declaration dans la source
     * @throws ErreurSemantique      erreur semantique dans la source
     */
    public Plic(String filePath) throws FileNotFoundException, ErreurSyntaxique, ErreurFile, DoubleDeclaration, ErreurSemantique {
        if (!filePath.endsWith(".plic")) throw new ErreurFile("Suffixe incorrect");
        File file = new File(filePath);
        if (!file.exists()) throw new ErreurFile("Fichier source absent");
        if (file.isDirectory()) throw new ErreurFile("Repertoire renseigné");
        if (file.isHidden()) throw new ErreurFile("Le fichier est caché");
        AnalyseurSyntaxique analyseurSyntaxique = new AnalyseurSyntaxique(file);
        Bloc bloc = analyseurSyntaxique.analyse();
        bloc.verifier();
        String code = bloc.toMips();
        System.out.println(code);
    }
}
