package sortir;

import java.util.Scanner;

import sortir.io.Writer;

class Sortir {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        int choice = getActionChoice(sc);

        if (choice == 1) {
            // rank

            // read in 1 list, and mention errors if any
            // go to other function
        } else if (choice == 2) {
            // merge

            // read in multiple lists, and mention errors if any
            // go to other function
        } else {
            ragequit();
        }


    }

    private static int getActionChoice(Scanner sc) throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            System.out.println(Writer.sayActionChoices(i));
            String input = sc.nextLine().trim();

            if (input.equals("1")) {
                return 1;
            } else if (input.equals("2")) {
                return 2;
            }
        }

        ragequit();
        return 0;   // to sate intellij
    }

    private static int getInputChoice(Scanner sc) throws InterruptedException {
        for (int i = 0; i < 4; i++) {
            System.out.println(Writer.sayInputChoices(i));
            String input = sc.nextLine().trim();

            if (input.equals("1")) {
                // do input
                return 1;
            } else if (input.equals("2")) {
                // do input
                return 2;
            }
        }

        ragequit();
        return 0;   // to sate intellij
    }

    private static void ragequit() throws InterruptedException {
        System.out.println(Writer.sayRagequit());
        Thread.sleep(2000);
        System.exit(0);
    }
}
