package de.idrinth.skyrim.autopatcher;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class PluginListParser {
    public void parse()
    {
        try {
            File file = new File("C:/Users/Björn/AppData/Local/Skyrim Special Edition/loadorder.txt");
            if (!file.isFile()) {
                return;
            }
            if (!file.canRead()) {
                return;
            }
            File file2 = new File("C:/Users/Björn/AppData/Local/Skyrim Special Edition/plugins.txt");
            if (!file2.isFile()) {
                return;
            }
            if (!file2.canRead()) {
                return;
            }
            BigInteger offset = BigInteger.ZERO;
            BigInteger increment = new BigInteger("16777216");
            BigInteger eslOffset = new BigInteger("4261412864");
            BigInteger eslIncrement = new BigInteger("4096");
            List<GameContentFile> loadOrder = new ArrayList<>();
            FormMap patch = new FormMap();
            List<ParseException> failures = new ArrayList<>();
            List<String> plugins = FileUtils.readLines(file2, "utf8");
            ParserFactory parser = new ParserFactory();
            for (String line : FileUtils.readLines(file, "utf8")) {
                if (!line.startsWith("#")) {
                    if (!line.endsWith(".esm") && !plugins.contains("*" + line)) {
                        continue;
                    }
                    boolean isESL = line.endsWith(".esl");
                    File dataFile = new File("C:/Program Files (x86)/Steam/steamapps/common/Skyrim Special Edition/Data/" + line);
                    if (!isESL) {
                        byte[] data = FileUtils.readFileToByteArray(dataFile);
                        isESL = ((data[9] >> 1) & 1) == 1;
                    }
                    if (isESL) {
                        loadOrder.add(new GameContentFile(line, loadOrder.size(), eslOffset));
                        eslOffset = eslOffset.add(eslIncrement);
                    } else {
                        loadOrder.add(new GameContentFile(line, loadOrder.size(), offset));
                        offset = offset.add(increment);
                    }
                    parser.es().run(dataFile, patch, failures);
                }
            }
        } catch (IOException ex) {
            return;
        }
    }
}
