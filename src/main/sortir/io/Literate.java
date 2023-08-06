package sortir.io;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import sortir.exc.BadFileException;
import sortir.exc.BadInputException;
import sortir.exc.RageQuitException;

public class Literate {
    private static final String TXT = ".txt";

    private final Reader rd;

    public Literate() {
        this.rd = new Reader();
    }

    private String get() {
        return this.rd.read();
    }

    private int getChoice(Consumer<Integer> sayer, int upperBound) throws RageQuitException {
        for (int i = 0; i < 4; i++) {
            sayer.accept(i);

            try {
                return this.rd.readInt(1, upperBound);
            } catch (BadInputException e) {
                // continue
            }
        }

        throw new RageQuitException();
    }

    private int getChoice(Consumer<Integer> sayer) throws RageQuitException {
        return getChoice(sayer, Integer.MAX_VALUE);
    }

    public int getActionChoice() throws RageQuitException {
        return getChoice(Writer::sayActionChoices, 2);
    }

    public int getInputChoice() throws RageQuitException {
        return getChoice(Writer::sayInputChoices, 2);
    }

    public int getRankChoice(String firstName, String secondName) throws RageQuitException {
        return getChoice(i -> Writer.sayRankChoices(firstName, secondName, i), 3);
    }

    public boolean confirm(List<String> list) throws RageQuitException {
        int value = getChoice(i -> Writer.sayConfirmChoices(list, i), 2);
        return (value == 1);
    }

    public int getInt() throws RageQuitException {
        return getChoice(Writer::sayGetInt);
    }

    public List<String> manualRankerInput() throws RageQuitException {
        List<String> inputs = new ArrayList<>();

        int count = getInt();
        for (int i = 0; i < count; i++) {
            String input = get();
            inputs.add(input);
        }

        Writer.saySuccess();
        return inputs;
    }

    public List<String> manualMergerInput() throws RageQuitException {
        List<String> inputs = new ArrayList<>();

        int count = getInt();

        Writer.sayMergerInputFormat();

        for (int i = 0; i < count; i++) {
            String input = get();
            inputs.add(input);
        }

        return inputs;
    }

    public List<String> readFromFile() throws RageQuitException {
        for (int i = 0; i < 4; i++) {
            Writer.sayGetFilePath(i);
            String filePath = get();

            if (!filePath.endsWith(TXT)) {
                filePath += TXT;
            }

            FileStuff file = new FileStuff(filePath);

            try {
                List<String> fileContent = file.loadFile();

                if (fileContent.size() < 1) {
                    throw new BadFileException("File is empty.");
                }

                Writer.saySuccess();
                return fileContent;
            } catch (FileNotFoundException e) {
                // continue
            } catch (BadFileException e) {
                Writer.sayFileEmpty();
                // continue
            }
        }

        throw new RageQuitException();
    }

    public void saveToFile(String fileContent) throws RageQuitException {
        for (int i = 0; i < 4; i++) {
            Writer.sayGetSaveFilePath(i);
            String filePath = get();

            if (!filePath.endsWith(TXT)) {
                filePath += TXT;
            }

            FileStuff file = new FileStuff(filePath + TXT);

            try {
                file.saveFile(fileContent);
                Writer.saySuccess();
            } catch (BadFileException e) {
                // continue
            }
        }

        throw new RageQuitException();
    }

    public boolean getSaveChoice() throws RageQuitException {
        int value = getChoice(Writer::saySaveChoices, 2);
        return (value == 1);
    }

    public boolean getRestartChoice() throws RageQuitException {
        int value = getChoice(Writer::sayRestartChoices, 2);
        return (value == 1);
    }

    public void bye() {
        Writer.sayBye();
    }

    public void sayFailedMerger(String errorMessage) {
        Writer.sayError("Invalid ranked list. Try again!", List.of(errorMessage));
    }
}
