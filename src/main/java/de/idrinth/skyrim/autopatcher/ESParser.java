package de.idrinth.skyrim.autopatcher;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public class ESParser extends AbstractFileParser
{
    @Override
    protected void parse(InputStream data, FormMap output, List<ParseException> failures) {
        try {
            System.out.println("File-Type: " + (char)data.read() + (char)data.read() + (char)data.read() + (char)data.read());
            System.out.println("Data-Size: " + data.read());
            data.skip(4);
            System.out.println("File-Flags: " + data.read());
            data.skip(27);
            BigInteger nextId = BigInteger.ZERO;
            BigInteger multiplier = BigInteger.valueOf(256);
            for (int i = 0; i < 4; i++) {
                nextId = nextId.multiply(multiplier).add(BigInteger.valueOf(data.read()));
            }
            System.out.println("Next-ID: " + nextId.toString(16));
        } catch (IOException ex) {
            return;
        }
    }

}
