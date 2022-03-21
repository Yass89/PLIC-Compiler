package plic.analyse;

import plic.Consts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author unshade
 */
public class AnalyseurLexical {

    /**
     * Scanneur
     */
    private final Scanner scanner;

    /**
     * Constructeur de l'analyseur lexical
     *
     * @param file fichier a analyser
     * @throws FileNotFoundException cas où le fichier est non trouvé
     */
    public AnalyseurLexical(File file) throws FileNotFoundException {
        this.scanner = new Scanner(file);
    }

    /**
     * Passer à l'unite lexicale suivante dans le fichier
     *
     * @return l'unite lexicale suivante
     */
    public String next() {
        String uniteLexicale = "";
        if (this.scanner.hasNext()) {
            uniteLexicale = this.scanner.next();

            // Verifier que l'unite lexicale n'est pas une ligne de commentaire
            while (uniteLexicale.startsWith(Consts.COMMENTAIRES)) {

                if (this.scanner.hasNext()) {
                    // Retirer la ligne de commentaire
                    this.scanner.nextLine();

                    // Verifier que la ligne suivant le commentaire n'est pas la fin de fichier
                    if (this.scanner.hasNext()) {

                        // Prendre la nouvelle valeur
                        uniteLexicale = this.scanner.next();
                    } else uniteLexicale = Consts.EOF;
                } else uniteLexicale = Consts.EOF;
            }
        } else uniteLexicale = Consts.EOF;
        return uniteLexicale;
    }

}
