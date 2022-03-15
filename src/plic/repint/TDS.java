package plic.repint;

import plic.exceptions.DoubleDeclaration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TDS {

    private int cptDepl;
    private Map<Entree, Symbole> tableSymboles;
    private static final TDS instance = new TDS();

    private TDS() {
        cptDepl = 4;
        tableSymboles = new HashMap<>();
    }

    public static TDS getInstance() {
        return instance;
    }

    public void ajouter(Entree e, Symbole s) throws DoubleDeclaration {
        if (!tableSymboles.containsValue(s)) {
            cptDepl -= 4;
            s.setDeplacement(cptDepl);
            this.tableSymboles.put(e, s);
        } else throw new DoubleDeclaration();
    }

    public int getCptDepl() {
        return cptDepl;
    }

    public Map<Entree, Symbole> getTableSymboles() {
        return tableSymboles;
    }
}
