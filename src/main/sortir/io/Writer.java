package sortir.io;

import java.util.List;

public class Writer {
    private static final String HAPPY_FACE = "(^-^)";
    private static final String ANGRY_FACE = "(-_-)";
    private static final String GAP = "     ";
    private static final List<String> noBody = List.of();
    private static final String RAGE_QUIT = "...";
    private static final String OI = "oi one more time ah";
    private static final String TIE = "[3] ~-TIE-~";
    
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
        int len = header.length() + 2;  // includes "~ ", but not the buffer before/after
        
        for (String line : body) {
            if (line.length() > len) {
                len = line.trim().length();
            }
        }
        
        String top =
                GAP + " " + repeat("_", len + 2) + "\n" +
                GAP + "|" + repeat(" ", len + 2) + "|\n";
        
        String headerLine =
                GAP + "| ~ " + header + repeat(" ", len - (header.length() + 2)) + " |\n";
        
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
        
        String bottom =
                GAP + "| " + repeat("_", len + 1) + "|\n" +
                GAP + "|/\n" +
                face;

        print(top + headerLine + bodyLines + bottom);
    }
    
    public static void sayHappy(String header, List<String> body) {
        say(HAPPY_FACE, header, body);
    }
    
    public static void sayHappy(String header) {
        say(HAPPY_FACE, header, noBody);
    }
    
    public static void sayAngry(String header, List<String> body) {
        say(ANGRY_FACE, header, body);
    }
    
    public static void sayAngry(String header) {
        say(ANGRY_FACE, header, noBody);
    }
    
    private static void sayHappyIndex(List<String> headerList, int i, List<String> body) {
        try {
            sayHappy(headerList.get(i), body);
        }
        catch (IndexOutOfBoundsException e) {
            sayHappy(headerList.get(headerList.size() - 1), body);
        }
    }
    
    public static void sayActionChoices(int i) {
        sayHappyIndex(ACTION_QUERY_HEADERS, i, ACTION_QUERY_BODY);
    }
    
    public static void sayInputChoices(int i) {
        sayHappyIndex(INPUT_QUERY_HEADERS, i, INPUT_QUERY_BODY);
    }
    
    public static void sayRageQuit() {
        sayAngry(RAGE_QUIT);
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
}
