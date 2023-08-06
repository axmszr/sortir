package sortir.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sortir.exc.BadFileException;

public class FileStuff {
    private final File file;

    public FileStuff(String path) {
        this.file = new File(path);
    }

    public void saveFile(String fileContent) throws BadFileException {
        if (this.file.exists()) {
            throw new BadFileException("File already exists.");
        }

        try {
            this.file.getParentFile().mkdir();
            this.file.createNewFile();

            FileWriter fw = new FileWriter(this.file);
            fw.write(fileContent);
            fw.close();
        } catch (IOException e) {
            throw new BadFileException("Some IOException.");
        }
    }

    public List<String> loadFile() throws FileNotFoundException {
        Scanner sc = new Scanner(this.file);
        List<String> lines = new ArrayList<>();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            lines.add(line);
        }

        return lines;
    }
}
