package sortir.io;

import java.util.List;

public class Writer {
    private static final String HAPPY_FACE = "(^-^)";
    private static final String ANGRY_FACE = "(-_-)";
    private static final String GAP = "     ";
    private static final List<String> noBody = List.of();
    
    private static final List<String> ACTION_QUERY_HEADERS = List.of(
            "Hello! How can I help you today?",
            "Type '1' or '2' to select!",
            "Just type '1' or '2'.",
            "oi one more time ah");
    
    private static final List<String> ACTION_QUERY_BODY = List.of(
            "[1] Rank a list",
            "[2] Merge two lists");
    
    private static final List<String> INPUT_QUERY_HEADERS = List.of(
            "Sure! How would you like to read that in?",
            "Type '1' or '2' to select!",
            "Just type '1' or '2'.",
            "oi one more time ah");
    
    private static final List<String> INPUT_QUERY_BODY = List.of(
            "[1] Manual input",
            "[2] Read from file");
    
    private static final String RAGEQUIT = "...";
    
    private static String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        
        return sb.toString();
    }
    
    private static String say(String face, String header, List<String> body) {
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

        return top + headerLine + bodyLines + bottom;
    }
    
    public static String sayHappy(String header, List<String> body) {
        return say(HAPPY_FACE, header, body);
    }
    
    public static String sayHappy(String header) {
        return say(HAPPY_FACE, header, noBody);
    }
    
    public static String sayAngry(String header, List<String> body) {
        return say(ANGRY_FACE, header, body);
    }
    
    public static String sayAngry(String header) {
        return say(ANGRY_FACE, header, noBody);
    }
    
    private static String sayHappyIndex(List<String> headerList, int i, List<String> body) {
        try {
            return sayHappy(headerList.get(i), body);
        }
        catch (IndexOutOfBoundsException e) {
            return sayHappy(headerList.get(headerList.size() - 1), body);
        }
    }
    
    public static String sayActionChoices(int i) {
        return sayHappyIndex(ACTION_QUERY_HEADERS, i, ACTION_QUERY_BODY);
    }
    
    public static String sayInputChoices(int i) {
        return sayHappyIndex(INPUT_QUERY_HEADERS, i, INPUT_QUERY_BODY);
    }
    
    public static String sayRagequit() {
        return sayAngry(RAGEQUIT);
    }

}
