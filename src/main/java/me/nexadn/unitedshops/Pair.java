package me.nexadn.unitedshops;

public class Pair<L, R> {
    public L first;
    public R second;

    public Pair()
    {
        this.first = null;
        this.second = null;
    }

    public Pair(L firstVal, R secondVal)
    {
        this.first = firstVal;
        this.second = secondVal;
    }

    @Override
    public String toString ()
    {
        return "Pair:" + this.first.toString() + ";" + this.second.toString();
    }
}
