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
            BigInteger espOffset = BigInteger.ZERO;
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
                    GameContentFile gcf = new GameContentFile(line, loadOrder.size());
                    loadOrder.add(gcf);
                    gcf.esm = line.endsWith(".esl");
                    gcf.esm = line.endsWith(".esm");
                    parser.es().parse(
                        FileUtils.openInputStream(
                            new File("C:/Program Files (x86)/Steam/steamapps/common/Skyrim Special Edition/Data/" + line)
                        ),
                        gcf,
                        patch,
                        failures,
                        eslOffset,
                        espOffset
                    );
                    if (gcf.esl) {
                        eslOffset = eslOffset.add(eslIncrement);
                    } else {
                        espOffset = espOffset.add(increment);
                    }
                }
            }
        } catch (IOException|InvalidFileTypeException ex) {
            return;
        }
    }
}
