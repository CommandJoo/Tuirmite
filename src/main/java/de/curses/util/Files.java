package de.curses.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

}
