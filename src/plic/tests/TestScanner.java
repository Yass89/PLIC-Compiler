package plic.tests;

import plic.analyse.AnalyseurLexical;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestScanner {
    public static void main(String[] args) throws FileNotFoundException {

        AnalyseurLexical analyseurLexical = new AnalyseurLexical(new File("src/plic/sources/plic0_1.plic"));
        String uniteLexicale = "";
        while (!uniteLexicale.equals("EOF")) {
            uniteLexicale = analyseurLexical.next();
            System.out.println(uniteLexicale);
        }
    }
}
