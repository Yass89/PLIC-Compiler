package plic.repint;

public class SymobleTableau extends Symbole{

    private int taille;

    public SymobleTableau(String type,int taille) {
        super(type,taille);
        this.taille = taille;
    }

    @Override
    public String getType() {
        return "Tableau";
    }
}
