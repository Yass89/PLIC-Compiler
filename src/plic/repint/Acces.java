package plic.repint;

public abstract class Acces extends Expression{
    public abstract String getAddress();

    @Override
    public String inverser() {
        if (!getNeg().equals(""))
            return "mul $v0, $v0, -1";
        else
            return "";
    }
}
