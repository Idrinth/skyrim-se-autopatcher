package de.idrinth.skyrim.autopatcher;

public class ParserFactory {
    private ESParser es;
    private BSAParser bsa;

    public ESParser es()
    {
        if (null != es) {
            return es;
        }
        return es = new ESParser();
    }
    public BSAParser bsa()
    {
        if (null != bsa) {
            return bsa;
        }
        return bsa = new BSAParser();
    }
}
