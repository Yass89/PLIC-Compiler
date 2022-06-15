package plic.repint;

import plic.exception.DoubleDeclaration;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TDS {

    private int cptDepl;
    private static TDS singleton = null;
    private HashMap<Entree,Symbole> hashMap;

    private TDS () {
        cptDepl = 0;
        hashMap = new HashMap<>();
    }

    public static synchronized TDS getInstance() {
        if (TDS.singleton==null) {
            singleton = new TDS();
        }
        return singleton;
    }

    public void ajouter(Entree e, Symbole s) throws DoubleDeclaration {
        if (hashMap.containsKey(e)) {
            throw new DoubleDeclaration("L'identifieur est déclaré plusieurs fois");
        } else {
            int i = 1;
            if (s instanceof SymobleTableau)
                i = s.getTaille();
            s.setDeplacement(cptDepl);
            cptDepl-=4*i;
            hashMap.put(e,s);
        }
    }

    public int hashMapSize() {
        AtomicInteger res = new AtomicInteger();
       hashMap.forEach((k,v)-> res.addAndGet(v.getTaille()));
       return res.get();
    }

    public int getCptDepl() {
        return cptDepl;
    }

    public Symbole getValue(Entree key) {
        return hashMap.get(key);
    }

    public boolean cleExiste(Entree key) {
        return hashMap.containsKey(key);
    }

    public Symbole identifier(Entree e) {
        return hashMap.getOrDefault(e, null);
    }
}
