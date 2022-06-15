package plic.tests;

import plic.exception.DoubleDeclaration;
import plic.repint.Entree;
import plic.repint.Symbole;
import plic.repint.TDS;

import static org.junit.Assert.*;

public class TDSTest {
    private TDS tds;
    private Entree e;
    private Symbole s;

    @org.junit.Before
    public void setUp() throws Exception {
        tds=TDS.getInstance();
        e = new Entree("coucou");
        s = new Symbole("entier");
    }

    @org.junit.Test
    public void getInstance() {
        assertEquals("Les instances devraient être les mêmes",tds,TDS.getInstance());
    }

    @org.junit.Test
    public void ajouter() {
        try {
            assertEquals("Le deplacement devrait commencer à 0",0,tds.getCptDepl());
            tds.ajouter(e,s);
            assertEquals("Un couple aurait dû être ajouter",1,tds.hashMapSize());
            assertTrue("La clé coucou de vrait exister",tds.cleExiste(e));
            assertEquals("Le symbole devrait être un entier", "entier", tds.getValue(e).getType());
            assertEquals("Le deplacement devrait être de 0", 0, tds.getValue(e).getDeplacement());
            Entree e1 = new Entree("cc");
            assertEquals("Le deplacement devrait être à -4",-4,tds.getCptDepl());
            tds.ajouter(e1,s);
            assertEquals("Un couple aurait dû être ajouter",2,tds.hashMapSize());
            assertTrue("La clé coucou de vrait exister",tds.cleExiste(e1));
            assertEquals("Le symbole devrait être un entier", "entier", tds.getValue(e).getType());
            assertEquals("Le deplacement devrait être de 0", -4, tds.getValue(e).getDeplacement());
            assertEquals("Le deplacement devrait être à -8",-8,tds.getCptDepl());
        } catch (DoubleDeclaration doubleDeclaration) {
            doubleDeclaration.printStackTrace();
        }
    }

    @org.junit.Test (expected = DoubleDeclaration.class)
    public void doubleDeclaration() throws DoubleDeclaration {
        tds.ajouter(e,s);
        tds.ajouter(e,s);
    }
}