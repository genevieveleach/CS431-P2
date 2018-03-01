public class Driver {
  String address;
  int rw;
  String value;
  int soft;
  int hard;
  int hit;
  String evicted;
  String dirty;
  boolean stop = false;
  PrintStream csv = new PrintStream(new FileOutputStream("Output.txt");
  System.setOut(csv);
  System.out.println("Address,r/w,value,soft,hard,hit,evicted_pg#,dirty_evicted_page");
  While (stop != true){
    System.out.println(address + "," + rw + "," + value + "," + soft + "," hard + "," + hit + "," + evicted + "," + dirty);
  }
}
