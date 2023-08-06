package sortir.io;

import java.util.List;
import java.util.stream.Collectors;

public class Writer {
    private static final String HAPPY_FACE = "(^-^)";
    private static final String ANGRY_FACE = "(-_-)";
    private static final String ERROR_FACE = "(x.x)";
    private static final String GAP = "     ";
    private static final List<String> noBody = List.of();
    private static final String HOW_EVEN = "how even...";
    private static final String RAGE_QUIT = "...";
    private static final String SUCCESS = "Done!";
    private static final String OI = "oi one more time ah";
    private static final String TIE = "[3] ~-TIE-~";
    private static final String FILE_EMPTY = "That file is empty :/";
    private static final String BYE = "Bye!";
    private static final String RANKER_INPUT_HEADER = "Great! Now list them out!";
    private static final String MERGER_INPUT_HEADER = "Ranked List Format:";

    private static final List<String> ACTION_QUERY_HEADERS = List.of(
            "Hello! How can I help you today?",
            "Type '1' or '2' to select!",
            "Just type '1' or '2'.",
            OI);

    private static final List<String> ACTION_QUERY_BODY = List.of(
            "[1] Rank a list",
            "[2] Merge two lists");

    private static final List<String> INPUT_QUERY_HEADERS = List.of(
            "Sure! How would you like to read that in?",
            "Type '1' or '2' to select!",
            "Just type '1' or '2'.",
            OI);

    private static final List<String> INPUT_QUERY_BODY = List.of(
            "[1] Manual input",
            "[2] Read from file");

    private static final List<String> RANK_QUERY_HEADERS = List.of(
            "Make your pick!",
            "Type '1' or '2' to select, or '3' to call a tie!",
            "Just type '1', '2' or '3'.",
            OI);

    private static final List<String> INT_QUERY_HEADERS = List.of(
            "How many would you like to input?",
            "Please provide an integer!",
            "Just type an integer",
            OI);

    private static final List<String> CONFIRM_QUERY_HEADERS = List.of(
            "Is this list correct?",
            "Type '1' to confirm, or '2' to try again!",
            "It's just '1' or '2'.",
            OI);

    private static final List<String> CONFIRM_QUERY_LEGS = List.of(
            "",
            "~-~-~",
            "",
            "[1] Confirm",
            "[2] Retry");

    private static final List<String> FILE_QUERY_HEADERS = List.of(
            "Okay! What's the file path?",
            "Please ensure the file is in the right folder!",
            "Does this file exist.",
            OI);

    private static final List<String> SAVE_QUERY_HEADERS = List.of(
            "Would you like to save this list?",
            "Type '1' to save, or '2' to skip!",
            "Just type '1' or '2'.",
            OI);

    private static final List<String> SAVE_QUERY_BODY = List.of(
            "[1] Save",
            "[2] Skip");

    private static final List<String> SAVE_FILE_QUERY_HEADERS = List.of(
            "Sure! What would you like to name the TXT file?",
            "Please use a unique TXT file name!",
            "That name is being used.",
            OI);

    private static final List<String> RESTART_QUERY_HEADERS = List.of(
            "Would you like to do something else?",
            "Type '1' to rank/merge again, or '2' to end the session!",
            "Just type '1' or '2'.",
            OI);

    private static final List<String> RESTART_QUERY_BODY = List.of(
            "[1] Continue",
            "[2] Close");

    private static final List<String> MERGER_INPUT_BODY = List.of(
            "- Please use the format: <rank> #; <item name>",
            "  e.g. 1 #; evermore",
            "",
            "- Please keep the items in ranked order, starting from 1.",
            "  The formatting check comes after all items are given!");

    private static String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < times; i++) {
            sb.append(str);
        }

        return sb.toString();
    }

    private static void print(String str) {
        System.out.println(str);
    }

    private static void say(String face, String header, List<String> body) {
        int len = header.length() + 2; // includes "~ ", but not the buffer before/after

        for (String line : body) {
            if (line.length() > len) {
                len = line.trim().length();
            }
        }

        String top = GAP + " " + repeat("_", len + 2) + "\n"
                + GAP + "|" + repeat(" ", len + 2) + "|\n";

        String headerLine = GAP + "| ~ " + header + repeat(" ", len - (header.length() + 2)) + " |\n";

        StringBuilder sbBody = new StringBuilder();
        if (body.size() > 0) {
            sbBody.append(GAP + "|")
                    .append(repeat(" ", len + 2))
                    .append("|\n");
        }
        for (String line : body) {
            sbBody.append(GAP + "| ")
                    .append(line)
                    .append(repeat(" ", len - line.length()))
                    .append(" |\n");
        }
        String bodyLines = sbBody.toString();

        String bottom = GAP + "| " + repeat("_", len + 1) + "|\n"
                + GAP + "|/\n"
                + face;

        print(top + headerLine + bodyLines + bottom);
    }

    public static void sayHappy(String header, List<String> body) {
        say(HAPPY_FACE, header, body);
    }

    public static void sayAngry(String header, List<String> body) {
        say(ANGRY_FACE, header, body);
    }

    public static void sayError(String header, List<String> body) {
        say(ERROR_FACE, header, body);
    }

    private static void sayHappyIndex(List<String> headerList, int i, List<String> body) {
        try {
            sayHappy(headerList.get(i), body);
        } catch (IndexOutOfBoundsException e) {
            sayHappy(headerList.get(headerList.size() - 1), body);
        }
    }

    public static void sayActionChoices(int i) {
        sayHappyIndex(ACTION_QUERY_HEADERS, i, ACTION_QUERY_BODY);
    }

    public static void sayInputChoices(int i) {
        sayHappyIndex(INPUT_QUERY_HEADERS, i, INPUT_QUERY_BODY);
    }

    public static void sayHowEven() {
        sayAngry(HOW_EVEN, noBody);
    }

    public static void sayRageQuit() {
        sayAngry(RAGE_QUIT, noBody);
    }

    public static void saySuccess() {
        sayHappy(SUCCESS, noBody);
    }

    private static List<String> makeRankBody(String firstName, String secondName) {
        String firstBody = "[1] " + firstName;
        String secondBody = "[2] " + secondName;
        return List.of(firstBody, secondBody, TIE);
    }

    public static void sayRankChoices(String firstName, String secondName, int i) {
        List<String> rankBody = makeRankBody(firstName, secondName);
        sayHappyIndex(RANK_QUERY_HEADERS, i, rankBody);
    }

    private static List<String> makeConfirmBody(List<String> list) {
        List<String> pointList = list.stream()
                .map(str -> "- " + str)
                .collect(Collectors.toList());
        pointList.addAll(CONFIRM_QUERY_LEGS);

        return pointList;
    }

    public static void sayConfirmChoices(List<String> list, int i) {
        List<String> body = makeConfirmBody(list);
        sayHappyIndex(CONFIRM_QUERY_HEADERS, i, body);
    }

    public static void sayGetInt(int i) {
        sayHappyIndex(INT_QUERY_HEADERS, i, noBody);
    }

    public static void sayGetFilePath(int i) {
        sayHappyIndex(FILE_QUERY_HEADERS, i, noBody);
    }

    public static void sayFileEmpty() {
        sayHappy(FILE_EMPTY, noBody);
    }

    public static void saySaveChoices(int i) {
        sayHappyIndex(SAVE_QUERY_HEADERS, i, SAVE_QUERY_BODY);
    }

    public static void sayGetSaveFilePath(int i) {
        sayHappyIndex(SAVE_FILE_QUERY_HEADERS, i, noBody);
    }

    public static void sayRestartChoices(int i) {
        sayHappyIndex(RESTART_QUERY_HEADERS, i, RESTART_QUERY_BODY);
    }

    public static void sayBye() {
        sayHappy(BYE, noBody);
    }

    public static void sayMergerInputFormat() {
        sayHappy(MERGER_INPUT_HEADER, MERGER_INPUT_BODY);
    }

    public static void sayRankerInputFormat() {
        sayHappy(RANKER_INPUT_HEADER, noBody);
    }
}
