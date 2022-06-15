package plic.repint;


import plic.exception.SemanticException;

public abstract class Operateur extends Expression{

    protected Expression e1;
    protected Expression e2;

    @Override
    public void verifier() throws SemanticException {
        e1.verifier();
        e2.verifier();
    }

    public void setE2(Expression e2) {
        this.e2 = e2;
    }

    public void setE1(Expression e1) {
        this.e1 = e1;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public String toMipsLHS() {
        return toMipsRHS();
    }

    @Override
    public String toMipsRHS() {
        return e1.toMipsRHS()+ "\n"+
                "\tsw $v0, ($sp)\n" +
                "\tsub $sp, $sp, 4\n" +
                e2.toMipsRHS() + "\n" +
                "\tadd $sp, $sp, 4\n" +
                "\tlw $v1, ($sp)\n" +
                "\t#test stackOverflow\n\tsgt $t1, $zero, $v1\n" +
                "\tsgt $t2, $zero, $v0\n" +
                "\t#operation a faire\n";
    }

}
