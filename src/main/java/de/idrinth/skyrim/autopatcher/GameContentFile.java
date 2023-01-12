package de.idrinth.skyrim.autopatcher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GameContentFile
{
    public final int loadOrder;
    public final String name;
    public BigInteger offset;
    public final List<Master> masters;
    public boolean esm;
    public boolean esl;
    private String padLeft(String s) {
        return String.format("%8s", s).replaceAll(" ", "0");  
    }
    public GameContentFile(String name, int loadOrder)
    {
        this.name = name;
        this.loadOrder = loadOrder;
        this.masters = new ArrayList<>();
    }
    public String toString()
    {
        return name + "[0x" + padLeft(offset.toString(16)) + "] (" + loadOrder + ")";
    }
}
