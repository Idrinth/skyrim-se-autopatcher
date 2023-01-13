package de.idrinth.skyrim.autopatcher;

import java.util.HashSet;
import java.util.Set;

class Form
{
    public final FormID id;
    public String editorId;
    public final Set<GameContentFile> definers;
    public final Set<Master> masters;

    public Form(FormID id, String editorId, GameContentFile definer, Set<Master> masters) {
        this.id = id;
        this.editorId = editorId;
        this.masters = new HashSet<>();
        for (Master m : masters) {
            if (m.file.defined.keySet().contains(id)) {
                this.masters.add(m);
            }
        }
        definers = new HashSet<>();
        this.definers.add(definer);
    }
}
