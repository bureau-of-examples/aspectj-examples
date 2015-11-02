package zhy2002.aspectjexamples.language;

/**
 * This code will compile even though in calc method we are accessing the private and protected fields of another object.
 */
public class CanAccessPrivateAndProtectedMembersViaReference {

    protected int val;

    public void setVal(int val) {
        this.val = val;
    }
}


class SubClass extends CanAccessPrivateAndProtectedMembersViaReference{

    private int factor;

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public int calc(SubClass other){
        return this.val * factor + other.val + other.factor;
    }
}
