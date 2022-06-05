package utility;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.in;
import static java.lang.System.out;
import static utility.Readline.readLine;

public class CollectingDataForCityCreator {
    public static String[] execute(String id) {
        String[] creator = new String[14];
        out.println("* - обязательные переменные");
        out.print("coord x* (double >-251): ");
        creator[0] = readLine(new BufferedInputStream(in));
        out.print("coord y* (double >-310): ");
        creator[1] = readLine(new BufferedInputStream(in));
        out.print("Сity name*: ");
        creator[2] = readLine(new BufferedInputStream(in));
        out.print("creation date* (dd-MM-yyyy): ");
        creator[3] = readLine(new BufferedInputStream(in));
        out.print("area* (double): ");
        creator[4] = readLine(new BufferedInputStream(in));
        out.print("population* (int): ");
        creator[5] = readLine(new BufferedInputStream(in));
        out.print("meters above sea level: ");
        creator[6] = readLine(new BufferedInputStream(in));
        out.print("climate(HUMIDSUBTROPICAL|OCEANIC|POLAR_ICECAP|RAIN_FOREST|SUBARCTIC): ");
        creator[7] = readLine(new BufferedInputStream(in));
        out.print("government*(ITMOCRACY|KLEPTOCRACY|MERITOCRACY|MONARCHY|TELLUROCRACY): ");
        creator[8] = readLine(new BufferedInputStream(in));
        out.print("standard of living(VERY_LOW|LOW|VERY_HIGH): ");
        creator[9] = readLine(new BufferedInputStream(in));
        out.print("Governor name: ");
        creator[10] = readLine(new BufferedInputStream(in));
        if (creator[10].equals("")) {
            creator[13] = "0";
            creator[11] = "";
        } else {
            creator[13] = "1";
            out.print("Governor birthday(dd-MM-yyyy hh:mm:ss): ");
            creator[11] = readLine(new BufferedInputStream(in));
        }
        if (Objects.isNull(id)){id="";}
        creator[12] = id;

        for (short i=0;i<14;i++){
            if (creator[i].equals("")){
                creator[i] = null;
            }
        }
        out.println(Arrays.toString(creator));
        return creator;
    }

    public static String[] execute() {
        return execute(null);
    }
}
