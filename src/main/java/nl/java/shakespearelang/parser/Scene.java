package nl.java.shakespearelang.parser;

import lombok.Getter;
import nl.java.shakespearelang.ParseException;
import nl.java.shakespearelang.parser.line.Assignment;
import nl.java.shakespearelang.parser.line.Conditional;
import nl.java.shakespearelang.parser.line.Enter;
import nl.java.shakespearelang.parser.line.Exit;
import nl.java.shakespearelang.parser.line.Goto;
import nl.java.shakespearelang.parser.line.InputStatement;
import nl.java.shakespearelang.parser.line.Line;
import nl.java.shakespearelang.parser.line.OutputStatement;
import nl.java.shakespearelang.parser.line.Pop;
import nl.java.shakespearelang.parser.line.Push;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nl.java.shakespearelang.parser.RomanToArabicConverter.romanToArabic;

@Getter
public class Scene {
    private String title;
    private int sceneNumber;
    private List<Line> lines = new ArrayList<>();

    public Scene(String sceneString, int number) {
        if (!sceneString.contains(".")) {
            throw new ParseException("Title of scene is not ended properly with a dot!");
        }
        String[] titleAndLines = sceneString.split("\\.");
        this.sceneNumber = checkSceneNumber(number, titleAndLines[0]);
        this.title = extractTitle(titleAndLines[0]);
        addLines(titleAndLines);
    }

    public Line getLine(int line) {
        return lines.get(line - 1);
    }

    private int checkSceneNumber(int number, String titleRaw) {
        if (!titleRaw.contains("scene ") || !titleRaw.contains(":")) {
            throw new ParseException("Title of scene does not contain 'scene' or a semicolumn!");
        }
        String romanNumeral = titleRaw.substring(0, titleRaw.indexOf(":")).replace("scene", "").trim();
        if (romanToArabic(romanNumeral) != number) {
            throw new ParseException("Scene numbering is not in sequence!");
        }
        return number;
    }

    private String extractTitle(String title) {
        Pattern pattern = Pattern.compile("scene\\s\\w.*:(.*?)");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        throw new ParseException("Not title in scene found!");
    }

    private void addLines(String[] titleAndLines) {
        String currentSubject = null;
        for (int i = 1; i < titleAndLines.length; i++) {
            String line = titleAndLines[i];
            if (line.contains("enter")) {
                lines.add(new Enter(line));
            } else if (line.contains("exit") || line.contains("exeunt")) {
                lines.add(new Exit(line));
            } else if (line.contains(":")) {
                currentSubject = line.substring(0, line.indexOf(':'));
                addLineHelper(currentSubject, line.substring(line.indexOf(':') + 1).trim());
            } else {
                addLineHelper(currentSubject, line);
            }
        }
    }

    private void addLineHelper(String currentSubject, String line) {
        line = line.trim();
        if (line.equals("listen to your heart") || line.equals("open your mind")) {
            lines.add(new InputStatement(currentSubject, line));
        } else if (line.equals("speak your mind") || line.equals("speak thy mind")) {
            lines.add(new OutputStatement(currentSubject, line, false));
        } else if (line.equals("open your heart")) {
            lines.add(new OutputStatement(currentSubject, line, true));
        } else if (line.startsWith("you")) {
            lines.add(new Assignment(currentSubject, line));
        } else if (line.startsWith("thou")) {
            lines.add(new Assignment(currentSubject, line));
        } else if (line.startsWith("art thou") || line.startsWith("are you")) {
            lines.add(new Conditional(currentSubject, line));
        } else if (line.startsWith("am i")) {
            lines.add(new Conditional(currentSubject, line));
        } else if (line.startsWith("is the")) {
            lines.add(new Conditional(currentSubject, line));
        } else if (line.matches("is (.*) worse than you")) {
            lines.add(new Conditional(currentSubject, line));
        } else if (line.startsWith("if not")) {
            lines.add(new Goto(currentSubject, line, false));
        } else if (line.startsWith("if")) {
            lines.add(new Goto(currentSubject, line, true));
        } else if (line.startsWith("let us") || line.startsWith("we must return to")) {
            lines.add(new Goto(currentSubject, line, null));
        } else if (line.startsWith("remember")) {
            lines.add(new Push(currentSubject, line));
        } else if (line.startsWith("recall")) {
            lines.add(new Pop(currentSubject, line));
        } else {
            throw new ParseException("type of line is unclear!" + line);
        }
    }

    public int getNumberOflines() {
        return lines.size();
    }
}
