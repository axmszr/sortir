package sortir.io;

import java.util.Scanner;

import sortir.exc.BadInputException;

public class Reader {
    private final Scanner sc;

    public Reader() {
        this.sc = new Scanner(System.in);
    }

    public String read() {
        return this.sc.nextLine().trim();
    }

    private int readInt() throws BadInputException {
        String str = read();
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new BadInputException("Not an integer.");
        }
    }

    public int readInt(int start, int end) throws BadInputException {
        int input = readInt();
        if (input < start) {
            throw new BadInputException("Input too small.");
        } else if (input > end) {
            throw new BadInputException("Input too large.");
        } else {
            return input;
        }
    }
}
