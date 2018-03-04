import java.util.*;
import java.io.*;

public class Driver {

    static String addr;
    static int rw;
    static String value;
    static int soft;
    static int hard;
    static int hit;
    static String evicted;
    static String dirty;
    static PrintStream csv;
    
    public static void main(String[] args) throws FileNotFoundException {
        String path = args[0];
        File file = new File(path);
        Scanner input = new Scanner(file);
        csv = new PrintStream(new FileOutputStream("output.csv"));
        writeHeaderToFile(csv);
        CPU.readFile(input);
    }

    private static void writeHeaderToFile(PrintStream csv) {
        csv.println("Address,R/W,Value,Soft Miss,Hard Miss,Hit,Evicted_Pg#,Dirty_Evicted_Page");
    }

    static void writeDataToFile(PrintStream csv) {
        csv.println(addr + "," + rw + "," + value + "," + soft + "," + hard + "," + hit + "," + evicted + "," + dirty);
    }

}
