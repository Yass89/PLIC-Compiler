package plic.tests;

import org.junit.Before;
import org.junit.Test;
import plic.repint.*;

import static org.junit.Assert.*;

public class BlocTest {

    private Bloc bloc;

    @Before
    public void setUp() throws Exception {
        bloc = new Bloc("x");
    }

    @Test
    public void ajouter() {
        Expression e = new Nombre(1);
        Instruction i = new Ecrire(e);
        bloc.ajouter(i);
        assertTrue("L'instruction i devrait être ajoutée",bloc.getInstruction(i));
        Expression e1 = new Idf("coucou");
        Instruction i1 = new Affectation(e1,new Idf("coucou"));
        bloc.ajouter(i1);
        assertTrue("L'instruction i devrait être ajoutée",bloc.getInstruction(i1));
    }
}