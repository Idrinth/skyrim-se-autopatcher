package de.idrinth.skyrim.autopatcher;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

public class ESParser
{
    protected void parse(InputStream data, GameContentFile file, FormMap output, List<ParseException> failures, BigInteger eslOffset, BigInteger espOffset) throws InvalidFileTypeException {
        try {
            String filetype = String.valueOf((char)data.read() + (char)data.read() + (char)data.read() + (char)data.read());
            if("TES4".equals(filetype)) {
                throw new InvalidFileTypeException(filetype + " is not supported");
            }
            System.out.println("Data-Size: " + data.read());
            data.skip(4);
            int flags = data.read();
            if (((flags >> 1) & 1) == 1) {
                file.esl = true;
            }
            if (((flags >> 1) & 1) == 1) {
                file.esm = true;
            }
            file.offset = file.esl ? eslOffset : espOffset;
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
