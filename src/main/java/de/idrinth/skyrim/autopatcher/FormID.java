package de.idrinth.skyrim.autopatcher;

import java.math.BigInteger;

public class FormID
{
    public final BigInteger id;
    public final GameContentFile file;
    public FormID(BigInteger id, GameContentFile file)
    {
        this.id = id;
        this.file = file;
    }
}
