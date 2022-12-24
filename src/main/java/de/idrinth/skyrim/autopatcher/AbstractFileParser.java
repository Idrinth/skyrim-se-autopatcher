package de.idrinth.skyrim.autopatcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.FileUtils;

abstract class AbstractFileParser
{    
    protected abstract void parse(InputStream data, FormMap output, List<ParseException> failures);

    public final void run(File file, FormMap data, List<ParseException> failures) {
        System.out.println(file.getName());
        try (InputStream stream = FileUtils.openInputStream(file)) {
            parse(stream, data, failures);
        } catch (IOException ex) {
            failures.add(new ParseException(file, ex.getMessage()));
        }
    }
}
