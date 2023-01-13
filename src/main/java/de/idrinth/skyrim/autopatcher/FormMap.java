package de.idrinth.skyrim.autopatcher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormMap extends HashMap<FormID, List<Form>>
{
    public static String padLeft(String s) {
        return String.format("%8s", s).replaceAll(" ", "0");  
    }
    public void set(BigInteger id, List<Master> masters, Form data)
    {
        int offset = Integer.parseInt(padLeft(id.toString(16)).substring(0, 2),16);
        Master master = masters.get(offset);
        FormID fid = new FormID(id.subtract(BigInteger.valueOf(offset).multiply(new BigInteger("16777216"))), master.file);
        if (!containsKey(fid)) {
            put(fid, new ArrayList<>());
            return;
        }
        for (Form f : get(fid)) {
            if (f.equals(data)) {
                f.masters.add(master);
                return;
            }
        }
        get(fid).add(data);
    }
    public byte[] convert()
    {
        return new byte[0];
    }
}
