package de.idrinth.skyrim.autopatcher;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameContentFile
{
    public final int loadOrder;
    public final String name;
    public BigInteger offset;
    public final Set<Master> masters;
    public boolean esm;
    public boolean esl;
    public boolean localized;
    public final Map<FormID, Form> defined;
    private String padLeft(String s) {
        return String.format("%8s", s).replaceAll(" ", "0");  
    }
    public GameContentFile(String name, int loadOrder)
    {
        this.name = name;
        this.loadOrder = loadOrder;
        this.masters = new HashSet<>();
        this.defined = new HashMap<>();
    }
    public String toString()
    {
        return name + "[0x" + padLeft(offset.toString(16)) + "] (" + loadOrder + ")";
    }
    public void write() throws IOException
    {
        
    }
}
