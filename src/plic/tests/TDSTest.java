package plic.tests;

import plic.exceptions.DoubleDeclaration;
import plic.repint.Entree;
import plic.repint.Symbole;
import plic.repint.TDS;

import static org.junit.Assert.*;

public class TDSTest {

    Entree e1;
    Entree e2;
    Entree e3;

    Symbole s1;
    Symbole s2;
    Symbole s3;

    @org.junit.Before
    public void setUp() throws Exception {
        e1 = new Entree("k");
        e2 = new Entree("k");
        e3 = new Entree("@");

        s1 = new Symbole("entier");
        s2 = new Symbole("entier");
        s3 = new Symbole("hugo");
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test (expected = DoubleDeclaration.class)
    public void getInstance() throws DoubleDeclaration {
        TDS.getInstance().ajouter(e1, s1);
        TDS.getInstance().ajouter(e2, s2);
    }

    @org.junit.Test
    public void ajouter() {
    }

}