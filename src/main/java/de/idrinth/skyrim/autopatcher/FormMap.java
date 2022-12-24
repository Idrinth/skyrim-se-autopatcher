package de.idrinth.skyrim.autopatcher;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormMap extends HashMap<FormID, HashMap<String, List<Form>>>
{
    public static String padLeft(String s) {
        return String.format("%8s", s).replaceAll(" ", "0");  
    }
    public void set(BigInteger id, List<Master> masters, String path, Form data)
    {
        int offset = Integer.parseInt(padLeft(id.toString(16)).substring(0, 2),16);
        Master master = masters.get(offset);
        FormID fid = new FormID(id.subtract(BigInteger.valueOf(offset).multiply(new BigInteger("16777216"))), master.file);
        if (!containsKey(fid)) {
            put(fid, new HashMap<>());
            get(fid).put(path, new ArrayList<>());
            return;
        }
        if (get(fid).get(path).get(0) != data) {
            get(fid).get(path).add(data);
        }
    }
    public byte[] convert()
    {
        return new byte[0];
    }
}
