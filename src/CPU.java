import java.util.*;
import java.io.*;

public class CPU {
  public static void readFile(){
    while(input.hasNextLine() == true){
      String line = input.nextLine();
      int addr = Integer.parseInt("0x" + line);
      int counter = 0;
      if (counter/20 == 0){
        OS.resetR();
        counter = 0;
      }
      - process and pass to mmu
      if (valid and Read){
        Physicalmem.Read(PF#, offset#);
      }
      else if (Valid){
        pm.write(PF#, offset, data);
      }
      else{
        OS.pull(PT#);
      }
      addr = 
      System.out.println(addr + "," + rw + "," + value + "," + soft + "," hard + "," + hit + "," + evicted + "," + dirty);
      counter++;
    }    
  }
  Class MMU{
    int R/W;
    int address; //what does this store?
    int data;
    public static void ReplaceTLBEntry(){
    }
    public static int getData(int, int, int){
      - Sets 3 ints
      if (check.TLB() > -1){// if valid
      }
      else if (check.Pagetable() > -1){// if valid
        h/m/s is set
      }
      if (r/w == 1){
        set d = 1
      }
      return page frame #
    }
  }
}
