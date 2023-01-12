package de.idrinth.skyrim.autopatcher;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ESParser
{
    protected void parse(InputStream data, GameContentFile file, FormMap output, List<ParseException> failures, BigInteger eslOffset, BigInteger espOffset) throws InvalidFileTypeException {
        try {
            String filetype = String.valueOf((char)data.read() + (char)data.read() + (char)data.read() + (char)data.read());
            if("TES4".equals(filetype)) {
                throw new InvalidFileTypeException(filetype + " is not supported");
            }
            System.out.println("Data-Size: " + data.read());
            data.skip(3);
            int flags1 = data.read();
            if (((flags1 >> 7) & 1) == 1) {
                file.localized = true;
            }
            if ((flags1 & 1) == 1) {
                file.esm = true;
            }
            int flags2 = data.read();
            if (((flags2 >> 1) & 1) == 1) {
                file.esl = true;
            }
            file.offset = file.esl ? eslOffset : espOffset;
            data.skip(27);
            BigInteger nextId = BigInteger.ZERO;
            BigInteger multiplier = BigInteger.valueOf(256);
            for (int i = 0; i < 4; i++) {
                nextId = nextId.multiply(multiplier).add(BigInteger.valueOf(data.read()));
            }
            System.out.println("Next-ID: " + nextId.toString(16));
            next("MAST", data);//skip to the master file, likely a bad idea
        } catch (IOException ex) {
            return;
        }
    }
    private void next(String key, InputStream stream) throws IOException
    {
        String prev = "";
        for (int i=0; i<4;i++) {
            prev = prev + (char) stream.read();
        }
        while (!prev.equals(key)) {
            prev = prev.substring(1) + (char) stream.read();
        }
    }
}
