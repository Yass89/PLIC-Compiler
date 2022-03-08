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
        String uniteLexicale;
        if (this.scanner.hasNext()) {
            uniteLexicale = this.scanner.next();
            if (uniteLexicale.startsWith("//")) {
                this.scanner.nextLine();
                uniteLexicale = this.scanner.next();
            }
            return uniteLexicale;
        } else return  "EOF";

    }
}
