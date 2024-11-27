package de.johannes.curses.util;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Files {


    public static void writeFile(String fileName, List<String> lines)
            throws IOException {
        if(!new File(fileName).exists()) new File(fileName).createNewFile();
        FileOutputStream outputStream = new FileOutputStream(fileName);
        for(String s : lines) {
            outputStream.write(s.getBytes());
        }
        outputStream.close();
    }

    public static void loadLibrary(String name) {
        String libName = System.mapLibraryName(name);
        try {
            InputStream inputStream = Files.class.getResourceAsStream("/"+libName);
            File fileOut = Files.getJarPath();
            File lib = new File(Files.getJarPath().getParentFile(), "/"+libName);
            if(lib.exists()) lib.delete();
            java.nio.file.Files.copy(inputStream, lib.toPath());

            System.load(lib.toString());
            inputStream.close();
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public static File getJarPath() {
        try {
            String path = Files.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            return new File(URLDecoder.decode(path, StandardCharsets.UTF_8));
        } catch (Exception _) {
            return null;
        }
    }

}
