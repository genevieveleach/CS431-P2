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

    public static void main(String[] args) throws FileNotFoundException {
        String path = args[0];
        File file = new File(path);
        Scanner input = new Scanner(file);
        PrintStream csv = new PrintStream(new FileOutputStream("Output.txt"));
        System.setOut(csv);
        System.out.println("Address,r/w,value,soft,hard,hit,evicted_pg#,dirty_evicted_page");
        CPU.readFile(input);
    }

    void writeDatatoFile() {

    }

}
