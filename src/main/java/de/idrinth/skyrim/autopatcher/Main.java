package de.idrinth.skyrim.autopatcher;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args)
    {
        PluginListParser p = new PluginListParser();
        FormMap data = p.parse();
        if (null != data) {
            Merger m = new Merger();
            try {
                m.merge(data);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
