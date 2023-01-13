package de.idrinth.skyrim.autopatcher;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

public class PluginListParser {
    public FormMap parse()
    {
        try {
            File config = new File(System.getProperty("user.home") + "/AppData/Local/Skyrim Special Edition");
            File file = new File(config + "/loadorder.txt");
            if (!file.isFile()) {
                return null;
            }
            if (!file.canRead()) {
                return null;
            }
            File file2 = new File(config + "/plugins.txt");
            if (!file2.isFile()) {
                return null;
            }
            if (!file2.canRead()) {
                return null;
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
            File base = new File(WindowsRegistry.readRegistry("HKLM\\SOFTWARE\\WOW6432Node\\Bethesda Softworks\\Skyrim Special Edition", "installed path"));
            for (String line : FileUtils.readLines(file, "utf8")) {
                if (!line.startsWith("#")) {
                    if (!line.endsWith(".esm") && !plugins.contains("*" + line)) {
                        continue;
                    }
                    if (line.startsWith("IdrinthAutoPatch")) {
                        continue;
                    }
                    GameContentFile gcf = new GameContentFile(line, loadOrder.size());
                    loadOrder.add(gcf);
                    gcf.esm = line.endsWith(".esl");
                    gcf.esm = line.endsWith(".esm");
                    parser.es().parse(
                        FileUtils.openInputStream(new File(base + "/Data/" + line)),
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
            return patch;
        } catch (IOException|InvalidFileTypeException ex) {
            return null;
        }
    }
}
