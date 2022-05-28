package utility;

import gorod.Human;

import java.io.BufferedInputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.System.in;
import static java.lang.System.out;
import static utility.Readline.readLine;

public class CollectingDataForGovernor {
    public static Human execute() {
        out.print("Governor name: ");
        String t = readLine(new BufferedInputStream(in));
        if (!t.equals("")) {
            Human governor = new Human(t);
            out.print("Governor birthday(dd-MM-yyyy HH:mm:ss): ");
            t = readLine(new BufferedInputStream(in));
            if (!t.equals("")) {
                try {
                    String pattern = "dd-MM-yyyy HH:mm:ss";
                    DateTimeFormatter Parser = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
                    governor.setBirthday(ZonedDateTime.parse(t, Parser));
                } catch (Exception e) {
                    out.println("дата фигня");
                }
            }
            return governor;
        }
        return null;
    }
}