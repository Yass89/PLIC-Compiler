package plic.repint;

import plic.exceptions.DoubleDeclaration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author unshade
 */
public class TDS {

    /**
     * Compteur du deplacement dans la pile
     */
    private int cptDepl;

    /**
     * Map des Entrees | Symboles
     */
    private Map<Entree, Symbole> tableSymboles;

    /**
     * Singleton de la TDS
     */
    private static final TDS instance = new TDS();

    /**
     * Constucteur de l'instance singleton TDS
     */
    private TDS() {
        cptDepl = 0;
        tableSymboles = new HashMap<>();
    }

    /**
     * Recuperer l'instance
     *
     * @return intance
     */
    public static TDS getInstance() {
        return instance;
    }

    /**
     * Ajouter une declaration a la TDS
     *
     * @param e entree
     * @param s symbole
     * @throws DoubleDeclaration cas de declaration double
     */
    public void ajouter(Entree e, Symbole s) throws DoubleDeclaration {
        if (!tableSymboles.containsKey(e)) {
            s.setDeplacement(cptDepl);
            this.tableSymboles.put(e, s);
            cptDepl -= 4;
            for(int i = 1;i<s.getTaille();i++){
                cptDepl -= 4;
            }
        } else throw new DoubleDeclaration("Double déclaration");
    }

    /**
     * Getter compteur deplacement
     *
     * @return competeur deplacement
     */
    public int getCptDepl() {
        return cptDepl;
    }

    /**
     * Identifier une entree
     *
     * @param e entree
     * @return le symbole de l'entree
     */
    public Symbole identifier(Entree e) {
        return this.tableSymboles.get(e);
    }

    /**
     * Getter table des symboles
     *
     * @return table des symboles
     */
    public Map<Entree, Symbole> getTableSymboles() {
        return tableSymboles;
    }

    @Override
    public String toString() {
        return "TDS{" +
                "cptDepl=" + cptDepl +
                ", tableSymboles=" + tableSymboles +
                '}';
    }
}
