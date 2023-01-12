package de.idrinth.skyrim.autopatcher;

import java.io.IOException;
import org.apache.commons.io.IOUtils;

/**
 * Based on work by
 * @author Oleg Ryaboy
 * @author Miguel Enriquez
 */
public class WindowsRegistry {

    /**
     *
     * @param location path in the registry
     * @param key registry key
     * @return registry value or null if not found
     */
    public static final String readRegistry(String location, String key) {
        try {
            // Run reg query, then read output with StreamReader (internal class)
            Process process = Runtime.getRuntime().exec("reg query "
                    + '"' + location + "\" /v \"" + key + '"');
            process.waitFor();
            String output = IOUtils.toString(process.getInputStream(), "ascii");
            if (output.contains("\t")) {
                String[] parsed = output.split("\t");
                return parsed[parsed.length - 1];
            }
            if (!output.contains("    ")) {
                return null;
            }
            String[] parsed = output.split("    ");
            return parsed[parsed.length - 1];
        } catch (IOException | InterruptedException e) {
            return null;
        }

    }
}
