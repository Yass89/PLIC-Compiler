package plic.analyse;

import plic.exception.SyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public final class AnalyseurLexical {

    private Scanner sc;
    public static final String EOF = "\t";
    public static boolean instruction = false;
    public static ArrayList<String> caracOK = new ArrayList<>(Arrays.asList("{", ";", "}", "[", "]", ":=", "+", "-", "*",
            "(", ")", ">", "<", "#", "<=", ">=", "=", "et", "ou",".."));

    public Token getToken() {
        return token;
    }

    private Token token;

    public AnalyseurLexical(File fichier) throws FileNotFoundException {
        sc = new Scanner(fichier);
        token = new Token();
    }

    public String next() throws SyntaxException {
        AnalyseurLexical.instruction = false;
        while (sc.hasNext()) {
            String s = sc.next();
            if (s.startsWith("//")) {
                if (sc.hasNextLine()) {
                    if (AnalyseurLexical.instruction) {
                        AnalyseurLexical.instruction = false;
                    } else {
                        token.augmenterLigne();
                    }
                    sc.nextLine();
                }
            } else {
                if (s.equals("{") || s.equals(";") || s.equals("}"))
                    token.augmenterLigne();
                else if (!caracOK.contains(s) && !(s.matches("[a-zA-Z]+") || s.matches("[0-9]+"))) {
                    throw new SyntaxException("'" + s + "' caractère inconnu");
                }
                AnalyseurLexical.instruction = true;
                return s;
            }
        }
        return EOF;
    }
}
