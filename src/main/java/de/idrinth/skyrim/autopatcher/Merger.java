package de.idrinth.skyrim.autopatcher;

import java.io.IOException;
import java.util.HashSet;

public class Merger {
    public void merge(FormMap data) throws IOException
    {
        GameContentFile gcf = new GameContentFile("IdrinthAutoPatch000.esp", 0);
        gcf.esl = true;
        for (FormID id: data.keySet()) {
            if (data.get(id).size() > 2) {
                Form result = new Form(id, data.get(id).get(0).editorId, gcf, new HashSet<>());
                if (!result.equals(data.get(id).get(data.get(id).size() - 1))) {
                    gcf.defined.put(id, result);
                    gcf.masters.addAll(result.masters);
                }
            }
        }
        gcf.write();
    }
}
