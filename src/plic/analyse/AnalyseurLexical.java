package plic.analyse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnalyseurLexical {

    private final Scanner scanner;


    public AnalyseurLexical(File file) throws FileNotFoundException {
        this.scanner = new Scanner(file);
    }

    public String next() {
        String uniteLexicale = "";
        if (this.scanner.hasNext()) {
            uniteLexicale = this.scanner.next();

            // Verifier que l'unite lexicale n'est pas une ligne de commentaire
            while (uniteLexicale.startsWith("//")) {

                if (this.scanner.hasNext()) {
                    // Retirer la ligne de commentaire
                    this.scanner.nextLine();

                    // Verifier que la ligne suivant le commentaire n'est pas la fin de fichier
                    if (this.scanner.hasNext()) {

                        // Prendre la nouvelle valeur
                        uniteLexicale = this.scanner.next();
                    } else uniteLexicale = "EOF";
                } else uniteLexicale = "EOF";
            }
        } else uniteLexicale = "EOF";
        return uniteLexicale;
    }
}
